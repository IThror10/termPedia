package com.TermPedia.factory.command.postgres;

import com.TermPedia.events.BaseEvent;
import com.TermPedia.events.data.AddTermEvent;
import com.TermPedia.factory.command.postgres.PostgresEventHandlerRequests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresEventHandlerRequestsTest {

    @Test
    void acceptEventQuery() throws Exception {
        //Arrange
        PostgresEventHandlerRequests handler = new PostgresEventHandlerRequests();
        BaseEvent event = new AddTermEvent("Term", "Description", 0);

        String query = "SELECT * FROM app.accept_event(%d, '%s', %d, '%s');".formatted(
                event.uid, event.dateTime.toString(), event.getEventType().ordinal(), event.getData());

        //Act
        String result = handler.acceptEventQuery(event);

        //Assert
        assertEquals(result, query);
    }
}