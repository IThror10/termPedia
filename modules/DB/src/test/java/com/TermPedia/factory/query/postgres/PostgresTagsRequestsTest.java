package com.TermPedia.factory.query.postgres;

import com.TermPedia.commands.events.EventType;
import com.TermPedia.factory.command.EventData;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.tags.FindTagByTermIdQuery;
import com.TermPedia.queries.user.UserTermTagRatingQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresTagsRequestsTest {

    @Test
    void getTagsByNameQuery() {
        //Arrange
        PostgresTagsRequests requests = new PostgresTagsRequests();
        FindTagByNameQuery settings = new FindTagByNameQuery(5, 10, "NAME");
        String expected = "SELECT name FROM data.tags WHERE lower(name) = lower('NAME') or plainto_tsquery('NAME') " +
                "@@ vector ORDER BY name LIMIT 5 OFFSET 10";

        //Act
        String query = requests.getTagsByNameQuery(settings);

        //Assert
        assertEquals(expected, query);
    }
    @Test
    void getTagsByTermIdQuery() {
        //Arrange
        PostgresTagsRequests requests = new PostgresTagsRequests();

        FindTagByTermIdQuery recentlySettings = new FindTagByTermIdQuery
                (5, 12, 13,true);
        String recentlyExpected = "SELECT tid, tag, rates_amount FROM data.terms_tags tt " +
                "WHERE tid = 13 ORDER BY rates_amount, tt.rating DESC LIMIT 5 OFFSET 12";

        FindTagByTermIdQuery bestSettings = new FindTagByTermIdQuery
                (5, 12, 13, false);
        String bestExpected = "SELECT tid, tag, rates_amount FROM data.terms_tags tt " +
                "WHERE tid = 13 ORDER BY tt.rating DESC, rates_amount DESC LIMIT 5 OFFSET 12";

        //Act
        String recentlyQuery = requests.getTagsByTermIdQuery(recentlySettings);
        String bestQuery = requests.getTagsByTermIdQuery(bestSettings);

        //Assert
        assertEquals(recentlyExpected, recentlyQuery);
        assertEquals(bestExpected, bestQuery);
    }

    @Test
    void addTagToTermQuery() {
        //Arrange
        PostgresTagsRequests requests = new PostgresTagsRequests();
        EventData data = new EventData("{JSON}", EventType.new_term.ordinal(), 0);
        String expected = "call data.add_tag_term('{JSON}');";

        //Act
        String query = requests.addTagToTermQuery(data);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void rateTagTermQuery() {
        //Arrange
        PostgresTagsRequests requests = new PostgresTagsRequests();
        EventData data = new EventData("{JSON}", EventType.change_term_tag_rating.ordinal(), 0);
        String expected = "call data.rate_tag_term('{JSON}', 0);";

        //Act
        String query = requests.rateTagTermQuery(data);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void userTermTagRatingTest() {
        //Arrange
        UserTermTagRatingQuery settings = new UserTermTagRatingQuery(0, 1, "tagName");
        String expect = "SELECT tag, rating FROM data.term_tag_rates WHERE uid = 0 and tid = 1 and tag = 'tagName'";
        PostgresTagsRequests builder = new PostgresTagsRequests();

        //Act
        String query = builder.userTermTagRating(settings);

        //Assert
        assertEquals(expect, query);
    }
}