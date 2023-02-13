package com.TermPedia.instances;

import com.TermPedia.factory.PostgresTestCommandConnection;
import com.TermPedia.factory.PostgresTestQueryConnection;
import com.TermPedia.dto.term.Term;
import com.TermPedia.factory.provider.ConstProvider;
import com.TermPedia.factory.command.SyncCommandFactory;
import com.TermPedia.factory.command.postgres.PostgresCommandFactory;
import com.TermPedia.factory.query.SyncQueryFactory;
import com.TermPedia.factory.query.postgres.PostgresQueryFactory;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.terms.FindTermByNameQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class termsTests {
    termsTests() {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new PostgresTestQueryConnection());
        SyncQueryFactory.setProvider(new ConstProvider("postgres"));

        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new PostgresTestCommandConnection());
        SyncCommandFactory.setProvider(new ConstProvider("postgres"));
    }

    @Test
    public void searchTerm() {
        //Arrange
        FindTermByNameQuery query = new FindTermByNameQuery(5, 0, "object");
        QueryHandler handler = new QueryHandler();

        //Act
        handler.handle(query);
        List<Term> terms = query.getResult().getTerms();

        //Assert
        assertEquals(2, terms.size());
        assertEquals("OOP", terms.get(0).getName());
        assertEquals("Singleton", terms.get(1).getName());
    }
}
