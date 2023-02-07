package TermPedia.instances;

import TermPedia.factory.PostgresTestCommandConnection;
import TermPedia.factory.PostgresTestQueryConnection;
import com.TermPedia.dto.Term;
import com.TermPedia.factory.ConstProvider;
import com.TermPedia.factory.command.SyncCommandFactory;
import com.TermPedia.factory.command.postgres.PostgresCommandConnection;
import com.TermPedia.factory.command.postgres.PostgresCommandFactory;
import com.TermPedia.factory.query.SyncQueryFactory;
import com.TermPedia.factory.query.postgres.PostgresQueryConnection;
import com.TermPedia.factory.query.postgres.PostgresQueryFactory;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.instances.terms.FindTermByNameQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

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
        FindTermByNameQuery query = new FindTermByNameQuery("object", 5, 0);
        QueryHandler handler = new QueryHandler();

        //Act
        handler.handle(query);
        Vector<Term> terms = query.getResult().getTerms();

        //Assert
        assertEquals(2, terms.size());
        assertEquals("OOP", terms.get(0).name);
        assertEquals("Singleton", terms.get(1).name);
    }
}
