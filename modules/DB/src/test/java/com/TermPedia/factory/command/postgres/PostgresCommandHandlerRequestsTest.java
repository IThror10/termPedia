package com.TermPedia.factory.command.postgres;

import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.commands.events.data.AddTermEvent;
import com.TermPedia.dto.term.Term;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresCommandHandlerRequestsTest {

    @Test
    void acceptEventQuery() throws Exception {
        //Arrange
        PostgresEventHandlerRequests handler = new PostgresEventHandlerRequests();
        DataEvent event = new AddTermEvent(0, new Term("Term", "Description"));

        String query = "SELECT * FROM app.accept_event(%d, '%s', %d, '%s');".formatted(
                event.getUid(), event.getDateTime().toString(), event.getEventType().ordinal(), event.getData());

        //Act
        String result = handler.acceptEventQuery(event);

        //Assert
        assertEquals(result, query);
    }
}