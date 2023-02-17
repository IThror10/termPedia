package com.TermPedia.instances;

import com.TermPedia.commands.events.data.AddTagToTermEvent;
import com.TermPedia.commands.events.data.ChangeTermTagRatingEvent;
import com.TermPedia.commands.result.EventResult;
import com.TermPedia.dto.tags.RatedTag;
import com.TermPedia.dto.tags.Tag;
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
import com.TermPedia.queries.results.tag.TagQueryResult;
import com.TermPedia.queries.results.user.UserRatingResult;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.tags.FindTagByTermIdQuery;
import com.TermPedia.queries.terms.FindTermByNameQuery;
import com.TermPedia.queries.user.UserTermTagRatingQuery;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TagsTests {
    TagsTests() {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new PostgresTestQueryConnection());
        SyncQueryFactory.setProvider(new ConstProvider("postgres"));

        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new PostgresTestCommandConnection());
        SyncCommandFactory.setProvider(new ConstProvider("postgres"));
    }

    @Test
    public void createTagTest() throws Exception {
        //Arrange
        IUpdater updater = SyncQueryFactory.instance().createUpdater();
        updater.setSynchronizer(SyncCommandFactory.instance().createSynchronizer());
        String tag = "Pain";

        //Act && Assert
        Term term = addTagToTerm(tag);
        Integer tid = term.getTid();
        changeTermTagRating(tid, tag);

        updater.update();
        findTagByTermId(tid, tag);
        findTagByName(tag);
        getUserTermTagRating(tid, tag);
    }

    private void getUserTermTagRating(Integer tid, String tag) {
        //Assert
        UserTermTagRatingQuery query = new UserTermTagRatingQuery(0, tid, tag);
        QueryHandler handler = new QueryHandler();

        //Act
        handler.handle(query);
        UserRatingResult result = query.getResult();

        //Assert
        assertEquals(5, result.getRating().getUserRating());
    }

    private void findTagByName(String tag) {
        //Assert
        FindTagByNameQuery query = new FindTagByNameQuery(5, 0, tag);
        QueryHandler handler = new QueryHandler();

        //Act
        handler.handle(query);
        TagQueryResult result = query.getResult();

        //Assert
        assertAll(
                () -> assertEquals(1, result.getTags().size()),
                () -> assertEquals(tag, result.getTags().get(0).getName())
        );
    }

    private void findTagByTermId(Integer tid, String tag) {
        //Assert
        FindTagByTermIdQuery query = new FindTagByTermIdQuery(5, 0, tid, true);
        QueryHandler handler = new QueryHandler();

        //Act
        handler.handle(query);
        RatedTag targetTag = null;
        for (RatedTag cur: query.getResult().getTags())
            if (Objects.equals(cur.getName(), tag))
                targetTag = cur;

        //Assert
        assertNotNull(targetTag);
        assertEquals(5.0, targetTag.getRating());
        assertEquals(1, targetTag.getRatesAmount());
    }

    private void changeTermTagRating(Integer tid, String tag) {
        //Arrange
        ChangeTermTagRatingEvent event = new ChangeTermTagRatingEvent(0, tid, tag, 5);
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(event);
        EventResult result = event.getResult();

        //Assert
        assertEquals("admin", result.getLogin());
    }

    private Term addTagToTerm(String tag) {
        //GetTermId
        QueryHandler qHandler = new QueryHandler();
        FindTermByNameQuery term = new FindTermByNameQuery(5, 0, "object");
        qHandler.handle(term);

        //Arrange
        Integer termId = term.getResult().getTerms().get(0).getTid();
        AddTagToTermEvent event = new AddTagToTermEvent(termId, tag, 0);
        CommandHandler handler = new CommandHandler();

        //Act
        handler.handle(event);
        EventResult result = event.getResult();

        //Assert
        assertEquals("admin", result.getLogin());
        return term.getResult().getTerms().get(0);
    }

    @Test
    public void tagUserRatingTest() {

    }
}
