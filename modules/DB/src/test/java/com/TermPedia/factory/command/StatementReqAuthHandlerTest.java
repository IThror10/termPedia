package com.TermPedia.factory.command;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.DataConflictException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.dto.exceptions.NotFoundException;
import com.TermPedia.dto.users.UserPrivateData;
import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.dto.users.User;
import com.TermPedia.factory.adapters.PostgresAdapter;
import com.TermPedia.factory.command.postgres.PostgresReqAuthHandlerRequests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StatementReqAuthHandlerTest {
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    StatementReqAuthHandlerTest() throws Exception {
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
    }
    @Test
    void register() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.getInt(any(String.class))).thenReturn(2).thenReturn(-1)
                .thenReturn(-2).thenReturn(-3);
        when(resultSet.getString(any(String.class))).thenReturn("Secret-String").thenReturn("admin")
                .thenReturn("babaYaga@yandex.ru").thenReturn(null).thenReturn(null);

        //Arrange
        StatementReqAuthHandler handler = new StatementReqAuthHandler(
                new PostgresAdapter(connection),
                new PostgresReqAuthHandlerRequests()
        );

        RegisterEvent userRepeat = new RegisterEvent("login", "password", "newEmail");
        RegisterEvent emailRepeat = new RegisterEvent("newLogin", "password", "email@ru");
        RegisterEvent wrongEmail = new RegisterEvent("newLogin", "password", "email");
        RegisterEvent successRegister = new RegisterEvent("newLogin", "password", "newEmail");

        //Act
        UserPrivateData data = handler.register(successRegister).getUserData();
        Executable userExists = () -> handler.register(userRepeat);
        Executable emailExists = () -> handler.register(emailRepeat);
        Executable emailFormatError = () -> handler.register(wrongEmail);

        //Assert
        assertAll(
                () -> assertEquals("admin", data.login()),
                () -> assertEquals("babaYaga@yandex.ru", data.email()),
                () -> assertEquals(0, data.phones().size()),
                () -> assertEquals(0, data.posts().size())
        );

        assertThrows(DataConflictException.class, userExists, "This Login is Already Used");
        assertThrows(DataConflictException.class, emailExists, "This Email is Already Used");
        assertThrows(FormatException.class, emailFormatError, "Wrong Email address format");
    }

    @Test
    void authorize() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(any(String.class))).thenReturn("Secret string").thenReturn("login")
            .thenReturn("email@yandex.ru").thenReturn(null).thenReturn(null);
        when(resultSet.getInt(any(String.class))).thenReturn(0).thenReturn(-1);

        //Arrange
        StatementReqAuthHandler handler = new StatementReqAuthHandler(
                new PostgresAdapter(connection),
                new PostgresReqAuthHandlerRequests()
        );

        AuthorizeEvent exception = new AuthorizeEvent("admin", "password");
        AuthorizeEvent logging = new AuthorizeEvent("some login", "password");

        //Act
        UserPrivateData data = handler.authorize(logging).getUserData();
        Executable noUser = () -> handler.authorize(exception);

        //Assert
        assertEquals("login", data.login());
        assertThrows(NotFoundException.class, noUser);
    }
}