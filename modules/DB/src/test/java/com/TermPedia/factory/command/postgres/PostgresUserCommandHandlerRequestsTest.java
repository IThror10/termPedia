package com.TermPedia.factory.command.postgres;

import com.TermPedia.commands.user.AuthorizeCommand;
import com.TermPedia.commands.events.data.RegisterEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresUserCommandHandlerRequestsTest {

    @Test
    void registerEventQuery() throws Exception {
        //Arrange
        PostgresReqAuthHandlerRequests handler = new PostgresReqAuthHandlerRequests();
        RegisterEvent event = new RegisterEvent("Login", "Password", "email@yandex.com");

        String query = "SELECT * FROM app.register_user('%s', '%s', %d);".formatted(
                event.getData(), event.getDateTime().toString(), event.getEventType().ordinal());

        //Act
        String result = handler.registerEventQuery(event);

        //Assert
        assertEquals(query, result);
    }

    @Test
    void authorizeEventQuery() throws Exception {
        //Arrange
        PostgresReqAuthHandlerRequests handler = new PostgresReqAuthHandlerRequests();
        AuthorizeCommand event = new AuthorizeCommand("Login", "Password");

        String query = "SELECT * FROM app.authorize_user('%s');".formatted(
                event.getData()
        );

        //Act
        String result = handler.authorizeEventQuery(event);

        //Assert
        assertEquals(result, query);
    }
}