package com.TermPedia.instances;

import com.TermPedia.commands.events.data.AddLitEvent;
import com.TermPedia.commands.events.data.AddLitToTermEvent;
import com.TermPedia.commands.events.data.ChangeTermLitRatingEvent;
import com.TermPedia.commands.result.EventResult;
import com.TermPedia.dto.literature.Literature;
import com.TermPedia.dto.term.Term;
import com.TermPedia.factory.PostgresTestCommandConnection;
import com.TermPedia.factory.PostgresTestQueryConnection;
import com.TermPedia.factory.provider.ConstProvider;
import com.TermPedia.factory.command.SyncCommandFactory;
import com.TermPedia.factory.command.postgres.PostgresCommandFactory;
import com.TermPedia.factory.query.IUpdater;
import com.TermPedia.factory.query.SyncQueryFactory;
import com.TermPedia.factory.query.postgres.PostgresQueryFactory;
import com.TermPedia.handlers.CommandHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.lit.*;
import com.TermPedia.queries.results.lit.LiteratureQueryResult;
import com.TermPedia.queries.results.lit.RatedLiteratureQueryResult;
import com.TermPedia.queries.results.lit.TagLiteratureQueryResult;
import com.TermPedia.queries.results.user.UserRatingResult;
import com.TermPedia.queries.terms.FindTermByNameQuery;
import com.TermPedia.queries.user.UserTermLitRatingQuery;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LitTests {
    LitTests() {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new PostgresTestQueryConnection());
        SyncQueryFactory.setProvider(new ConstProvider("postgres"));

        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new PostgresTestCommandConnection());
        SyncCommandFactory.setProvider(new ConstProvider("postgres"));
    }
    @Test
    public void createLitTest() throws Exception {
        //Arrange
        IUpdater updater = SyncQueryFactory.instance().createUpdater();
        updater.setSynchronizer(SyncCommandFactory.instance().createSynchronizer());

        List<String> authors = new ArrayList<>();
        authors.add("George Orwell");
        Literature lit = new Literature("1984", "Novel", 1949, authors);

        //Act && Assert
        createLit(lit);
        updater.update();

        Integer lid = findLitByAuthor(lit);
        findLitByName(lit);

        Term term = addTermToLitTest(lid);
        Integer tid = term.getTid();
        changeTermLitRating(tid, lid);
        updater.update();
        findLitByTermId(tid, lit);
        findLitByTermName(term.getName(), lit);
        getUserTermLitRating(tid, lid);
    }

    private void findLitByTermName(String name, Literature lit) {
        //Assert
        QueryHandler handler = new QueryHandler();
        FindLitByLikeTermNameQuery query = new FindLitByLikeTermNameQuery(5, 0, name);

        //Act
        handler.handle(query);
        RatedLiteratureQueryResult result = query.getResult();

        //Assert
        assertAll(
                () -> assertEquals(1, result.getBooks().size()),
                () -> assertEquals(lit.getYear(), result.getBooks().get(0).getYear()),
                () -> assertEquals(1.0, result.getBooks().get(0).getRating()),
                () -> assertEquals(1, result.getBooks().get(0).getRatesAmount())
        );
    }

    private void getUserTermLitRating(Integer tid, Integer lid) {
        //Assert
        UserTermLitRatingQuery query = new UserTermLitRatingQuery(0, tid, lid);
        QueryHandler handler = new QueryHandler();

        //Act
        handler.handle(query);
        UserRatingResult result = query.getResult();

        //Assert
        assertEquals(1, result.getResult().get(0).getUserRating());
    }

    private void changeTermLitRating(Integer tid, Integer lid) {
        //Arrange
        ChangeTermLitRatingEvent event = new ChangeTermLitRatingEvent(0, tid, lid, 1);
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(event);
        EventResult result = event.getResult();

        //Assert
        assertEquals("admin", result.getLogin());
    }

    private void findLitByTermId(Integer tid, Literature lit) {
        //Assert
        QueryHandler handler = new QueryHandler();
        FindLitByTermIdQuery query = new FindLitByTermIdQuery(5, 0, tid);

        //Act
        handler.handle(query);
        RatedLiteratureQueryResult result = query.getResult();

        //Assert
        assertAll(
                () -> assertEquals(1, result.getBooks().size()),
                () -> assertEquals(lit.getYear(), result.getBooks().get(0).getYear()),
                () -> assertEquals(1.0, result.getBooks().get(0).getRating()),
                () -> assertEquals(1, result.getBooks().get(0).getRatesAmount())
        );
    }

    private void findLitByName(Literature lit) {
        //Assert
        FindLitByLikeNameQuery query = new FindLitByLikeNameQuery(5, 0, lit.getName());
        query.setLitType(lit.getType());
        QueryHandler handler = new QueryHandler();

        //Act
        handler.handle(query);
        TagLiteratureQueryResult result = query.getResult();

        //Assert
        assertAll(
                () -> assertEquals(1, result.getBooks().size()),
                () -> assertEquals(lit.getYear(), result.getBooks().get(0).getYear()),
                () -> assertEquals(0.0, result.getBooks().get(0).getRating())
        );
    }

    private Integer findLitByAuthor(Literature lit) {
        //Assert
        FindLitByAuthorNameQuery query = new FindLitByAuthorNameQuery(5, 0, lit.getAuthors().get(0));
        query.setLitType(lit.getType());
        QueryHandler handler = new QueryHandler();

        //Act
        handler.handle(query);
        LiteratureQueryResult result = query.getResult();

        //Assert
        assertAll(
                () -> assertEquals(1, result.getBooks().size()),
                () -> assertEquals(lit.getYear(), result.getBooks().get(0).getYear()),
                () -> assertEquals(lit.getName(), result.getBooks().get(0).getName())
        );
        return result.getBooks().get(0).getLid();
    }

    private void createLit(Literature literature) {
        //Arrange
        AddLitEvent event = new AddLitEvent(0, literature);
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(event);
        EventResult result = event.getResult();

        //Assert
        assertEquals("admin", result.getLogin());
    }

    private Term addTermToLitTest(Integer lid) {
        //GetTermId
        QueryHandler qHandler = new QueryHandler();
        FindTermByNameQuery term = new FindTermByNameQuery(5, 0, "object");
        qHandler.handle(term);

        //Arrange
        Integer termId = term.getResult().getTerms().get(0).getTid();
        AddLitToTermEvent event = new AddLitToTermEvent(termId, lid, 0);
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(event);
        EventResult result = event.getResult();

        //Assert
        assertEquals("admin", result.getLogin());
        return term.getResult().getTerms().get(0);
    }


    @Test
    public void userRatingTest() {

    }
}
