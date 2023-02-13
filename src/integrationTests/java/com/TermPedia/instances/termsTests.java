package com.TermPedia.instances;

import com.TermPedia.commands.events.data.AddTermEvent;
import com.TermPedia.commands.result.EventResult;
import com.TermPedia.factory.PostgresTestCommandConnection;
import com.TermPedia.factory.PostgresTestQueryConnection;
import com.TermPedia.dto.term.Term;
import com.TermPedia.factory.provider.ConstProvider;
import com.TermPedia.factory.command.SyncCommandFactory;
import com.TermPedia.factory.command.postgres.PostgresCommandFactory;
import com.TermPedia.factory.query.IUpdater;
import com.TermPedia.factory.query.SyncQueryFactory;
import com.TermPedia.factory.query.postgres.PostgresQueryFactory;
import com.TermPedia.handlers.CommandHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.results.term.TermQueryResult;
import com.TermPedia.queries.terms.FindTermByNameQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TermsTests {
    TermsTests() {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new PostgresTestQueryConnection());
        SyncQueryFactory.setProvider(new ConstProvider("postgres"));

        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new PostgresTestCommandConnection());
        SyncCommandFactory.setProvider(new ConstProvider("postgres"));
    }

    @Test
    public void createTermTest() throws Exception {
        IUpdater updater = SyncQueryFactory.instance().createUpdater();
        updater.setSynchronizer(SyncCommandFactory.instance().createSynchronizer());

        String termName = "SomeWeirdTerm";
        String description = "EvenWeirderDescription";

        createTerm(termName, description);
        updater.update();
        findTermByName(termName);
    }

    private void createTerm(String name, String description) {
        //Arrange
        Term addTerm = new Term(name, description);
        AddTermEvent event = new AddTermEvent(0, addTerm);
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(event);
        EventResult result = event.getResult();

        //Assert
        assertEquals("admin", result.getLogin());
    }

    private void findTermByName(String name) {
        //Arrange
        FindTermByNameQuery query = new FindTermByNameQuery(10, 0, name);
        QueryHandler handler = new QueryHandler();

        //Act
        handler.handle(query);
        TermQueryResult result = query.getResult();

        //Assert
        assertAll(
                () -> assertEquals(1, result.getTerms().size()),
                () -> assertEquals(name, result.getTerms().get(0).getName())
        );
    }
}
