package com.TermPedia.instances;

import com.TermPedia.factory.PostgresTestCommandConnection;
import com.TermPedia.factory.PostgresTestQueryConnection;
import com.TermPedia.commands.events.data.RegisterEvent;
import com.TermPedia.commands.result.EventResult;
import com.TermPedia.commands.user.AuthorizeCommand;
import com.TermPedia.dto.exceptions.DataConflictException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.dto.exceptions.NotFoundException;
import com.TermPedia.dto.users.*;
import com.TermPedia.commands.user.*;
import com.TermPedia.factory.provider.ConstProvider;
import com.TermPedia.factory.command.SyncCommandFactory;
import com.TermPedia.factory.command.postgres.PostgresCommandFactory;
import com.TermPedia.factory.query.SyncQueryFactory;
import com.TermPedia.factory.query.postgres.PostgresQueryFactory;
import com.TermPedia.handlers.CommandHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.user.GetUserPublicDataQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {
    UserTests() {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new PostgresTestQueryConnection());
        SyncQueryFactory.setProvider(new ConstProvider("postgres"));

        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new PostgresTestCommandConnection());
        SyncCommandFactory.setProvider(new ConstProvider("postgres"));
    }

    @Test
    public void getUserPublicDataTest() {
        //Arrange
        GetUserPublicDataQuery query = new GetUserPublicDataQuery("admin");
        QueryHandler handler = new QueryHandler();

        //Act
        handler.handle(query);
        UserPublicData data = query.getResult().getUser();

        //Assert
        assertEquals("admin", data.getLogin());
        assertEquals("admin@gmail.com", data.getEmail());
        assertAll(
                () -> assertEquals(2, data.getPhones().size()),
                () -> assertEquals("+7-800-555-35-35", data.getPhones().get(0)),
                () -> assertEquals("+7-777-777-77-77", data.getPhones().get(1))
        );
        assertEquals(0, data.getPosts().size());
    }

    @Test
    public void changePublicDataTest() {
        //Arrange
        ChangeContactsCommand addPhone = new ChangeContactsCommand(1, "/phone", "add", "212-85-06");
        ChangeContactsCommand addPost = new ChangeContactsCommand(1, "/post", "add", "Student");
        ChangeContactsCommand removePhone = new ChangeContactsCommand(1, "/phone", "remove", "212-85-06");
        ChangeContactsCommand unknownColumn = new ChangeContactsCommand(1, "/uid", "remove", "123456");
        ChangeContactsCommand unknownOp = new ChangeContactsCommand(1, "/phone", "replace", "666-666");
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(addPhone);
        handler.handle(addPost);
        handler.handle(removePhone);
        Executable columnNotFound = () -> handler.handle(unknownColumn);
        Executable operationNotFound = () -> handler.handle(unknownOp);

        UserPublicData newPhone = addPhone.getResult().getData();
        UserPublicData newPost = addPost.getResult().getData();
        UserPublicData phoneRemoved = removePhone.getResult().getData();

        //Assert
        assertThrows(NotFoundException.class, columnNotFound, "Change target not found");
        assertThrows(NotFoundException.class, operationNotFound, "Operation not found");

        assertAll(
                () -> assertEquals(1, newPhone.getPhones().size()),
                () -> assertEquals("212-85-06", newPhone.getPhones().get(0)),
                () -> assertEquals(0, newPhone.getPosts().size())
        );
        assertAll(
                () -> assertEquals(1, newPost.getPhones().size()),
                () -> assertEquals("212-85-06", newPost.getPhones().get(0)),
                () -> assertEquals(1, newPost.getPosts().size()),
                () -> assertEquals("Student", newPost.getPosts().get(0))
        );
        assertAll(
                () -> assertEquals(0, phoneRemoved.getPhones().size()),
                () -> assertEquals(1, phoneRemoved.getPosts().size()),
                () -> assertEquals("Student", phoneRemoved.getPosts().get(0))
        );
    }
    @Test
    public void registerLogoutTest() {
        String login = "thror";
        String password = "secretPassword";
        String email = "babaYaga@yandex.ru";

        UserPrivateData data;
        Integer uid;

        registerUserTest(login, password, email);
        data = authorizeUserTest(login, password);

        uid = sessionIsValidTest(data.getLogin(), data.getSecret(), true);
        logoutTest(uid, login);
        sessionIsValidTest(data.getLogin(), data.getSecret(), false);
    }

    private void registerUserTest(String login, String password, String email) {
        //Arrange
        RegisterEvent newUser = new RegisterEvent(login, password, email);
        RegisterEvent oldLogin = new RegisterEvent(login, password, email + "s");
        RegisterEvent oldEmail = new RegisterEvent(login + "a", password, email);
        RegisterEvent wrongEmail = new RegisterEvent(login + "a", password, "daw");
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(newUser);
        EventResult result = newUser.getResult();
        Executable loginError = () -> handler.handle(oldLogin);
        Executable emailError = () -> handler.handle(oldEmail);
        Executable formatError = () -> handler.handle(wrongEmail);

        //Assert
        assertEquals("thror", result.getLogin());
        assertThrows(DataConflictException.class, loginError);
        assertThrows(DataConflictException.class, emailError);
        assertThrows(FormatException.class, formatError);
    }

    private UserPrivateData authorizeUserTest(String login, String password) {
        //Arrange
        AuthorizeCommand success = new AuthorizeCommand(login, password);
        AuthorizeCommand failure = new AuthorizeCommand(login, "wrong password");
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(success);
        UserPrivateData data = success.getResult().getData();
        Executable notFound = () -> handler.handle(failure);

        //Assert
        assertNotNull(data.getSecret());
        assertEquals(login, data.getLogin());
        assertThrows(NotFoundException.class, notFound);

        return data;
    }

    private Integer sessionIsValidTest(String login, String secret, boolean expectedValid) {
        //Arrange
        ValidateCommand event = new ValidateCommand(login, secret);
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(event);
        UserValidData data = event.getResult().getData();

        //Assert
        if (expectedValid) {
            assertNotNull(data.getUserId());
            assertEquals(login, data.getLogin());
        } else {
            assertNull(data.getUserId());
            assertNull(data.getLogin());
        }
        return data.getUserId();
    }

    private void logoutTest(Integer uid, String login) {
        //Arrange
        LogoutCommand logout = new LogoutCommand(uid);
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(logout);
        EventResult result = logout.getStatus();

        //Assert
        assertEquals(login, result.getLogin());
    }
}
