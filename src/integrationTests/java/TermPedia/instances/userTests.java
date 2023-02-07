package TermPedia.instances;

import TermPedia.factory.PostgresTestCommandConnection;
import TermPedia.factory.PostgresTestQueryConnection;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.factory.ConstProvider;
import com.TermPedia.factory.command.ReqAuthHandler;
import com.TermPedia.factory.command.SyncCommandFactory;
import com.TermPedia.factory.command.postgres.PostgresCommandFactory;
import com.TermPedia.factory.query.SyncQueryFactory;
import com.TermPedia.factory.query.postgres.PostgresQueryFactory;
import com.TermPedia.handlers.EventHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.instances.users.GetUserPublicDataQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class userTests {
    userTests() {
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
        UserPublicData data = query.getResult().getUserData();

        //Assert
        assertEquals(0, data.userID());
        assertEquals("admin", data.login());
        assertEquals("admin@gmail.com", data.email());
        assertAll(
                () -> assertEquals(2, data.phones().size()),
                () -> assertEquals("+7-800-555-35-35", data.phones().get(0)),
                () -> assertEquals("+7-777-777-77-77", data.phones().get(1))
        );
        assertEquals(0, data.posts().size());
    }

    @Test
    public void authorizeUser() {
        //Arrange
        AuthorizeEvent event = new AuthorizeEvent("admin", "password");
        AuthorizeEvent wrongData = new AuthorizeEvent("admin", "password123");
        EventHandler handler = new EventHandler();

        //Act
        handler.handle(event);
        UserPublicData data = event.getResult().getUserData();
        Executable wrongPassword = () -> handler.handle(wrongData);

        //Assert
        assertEquals(0, data.userID());
        assertEquals("admin", data.login());
        assertEquals("admin@gmail.com", data.email());
        assertAll(
                () -> assertEquals(2, data.phones().size()),
                () -> assertEquals("+7-800-555-35-35", data.phones().get(0)),
                () -> assertEquals("+7-777-777-77-77", data.phones().get(1))
        );
        assertEquals(0, data.posts().size());
        assertThrows(ActionsException.class, wrongPassword);
    }

    @Test
    public void registerUser() {
        //Arrange
        RegisterEvent event = new RegisterEvent("thror", "secret", "babaYaga@yandex.ru");
        EventHandler handler = new EventHandler();

        //Act
        handler.handle(event);
        UserPublicData data = event.getResult().getUserData();

        //Assert
        assertEquals(1, data.userID());
        assertEquals("thror", data.login());
        assertEquals("babaYaga@yandex.ru", data.email());
        assertEquals(0, data.phones().size());
        assertEquals(0, data.posts().size());
    }
}
