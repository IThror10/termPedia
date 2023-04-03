package com.TermPedia.factory.query.postgres;

import com.TermPedia.commands.events.EventType;
import com.TermPedia.factory.command.EventData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresUsersRequestsTest {

    @Test
    void addUserQuery() {
        //Arrange
        PostgresUsersRequests requests = new PostgresUsersRequests();
        EventData data = new EventData("{JSON}", EventType.registration.ordinal(), 0);
        String expected = "call data.add_user(0);";

        //Act
        String query = requests.addUserQuery(data);

        //Assert
        assertEquals(expected, query);
    }
}