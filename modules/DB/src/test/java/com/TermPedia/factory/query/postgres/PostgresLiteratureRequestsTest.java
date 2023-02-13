package com.TermPedia.factory.query.postgres;

import com.TermPedia.queries.lit.*;
import com.TermPedia.queries.user.UserTermLitRatingQuery;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PostgresLiteratureRequestsTest {

    @Test
    void authorSearchQuery() {
        //Arrange
        PostgresLiteratureRequests requests = new PostgresLiteratureRequests();
        FindLitByAuthorNameQuery noType = new FindLitByAuthorNameQuery(10, 5, "NAME");
        noType.setYearStart(2000);
        noType.setYearEnd(3000);

        FindLitByAuthorNameQuery withType = new FindLitByAuthorNameQuery(10, 5, "NAME");
        withType.setLitType("TYPE");
        withType.setYearStart(2000);
        withType.setYearEnd(3000);

        String expectedNoType = "SELECT DISTINCT l.name, l.type, l.year, l.authors FROM (SELECT name FROM data.authors " +
                "WHERE lower(name) like lower('%NAME%')) as a JOIN data.authors_lit al on a.name = al.author " +
                "JOIN data.lit l on l.lid = al.lid WHERE l.year >= 2000 and l.year <= 3000 " +
                "ORDER BY l.name LIMIT 10 OFFSET 5";

        String expectedType = "SELECT DISTINCT l.name, l.type, l.year, l.authors FROM (SELECT name FROM data.authors " +
                "WHERE lower(name) like lower('%NAME%')) as a JOIN data.authors_lit al on a.name = al.author " +
                "JOIN data.lit l on l.lid = al.lid WHERE l.year >= 2000 and l.year <= 3000 and l.type = TYPE " +
                "ORDER BY l.name LIMIT 10 OFFSET 5";

        //Act
        String typeQuery = requests.authorSearchQuery(withType);
        String noTypeQuery = requests.authorSearchQuery(noType);

        //Assert
        assertEquals(expectedNoType, noTypeQuery);
        assertEquals(expectedType, typeQuery);
    }

    @Test
    void bookSearchQuery() {
        //Arrange
        PostgresLiteratureRequests requests = new PostgresLiteratureRequests();
        Vector<String> tags = new Vector<>();
        tags.add("TAG1");
        tags.add("TAG2");

        String withBestRatingType =  "SELECT bs.lid, name, type, year, authors, min(rating) as rating FROM " +
                "(SELECT LID FROM data.lit WHERE lower(name) like lower('%NAME%') or vector @@ " +
                "plainto_tsquery('NAME')) as l JOIN data.book_search bs on l.LID = bs.LID WHERE " +
                "year >= 0 and year <= 3000 and type = 'TYPE' and (tag = 'TAG1' and rating >= 5.0 or " +
                "tag = 'TAG2' and rating >= 5.0) GROUP BY (bs.lid, name, type, year, authors) HAVING count(*) = 2 " +
                "ORDER BY rating DESC LIMIT 5 OFFSET 5";
        FindLitByLikeNameQuery ratingSettings = new FindLitByLikeNameQuery(5, 5, "NAME");
        ratingSettings.setYearStart(0);
        ratingSettings.setMinRating(5.0);
        ratingSettings.setOrderByRating(true);
        ratingSettings.setLitType("TYPE");
        ratingSettings.setTags(tags);

        String noRating = "SELECT bs.lid, name, type, year, authors, rating as rating FROM " +
                "(SELECT LID FROM data.lit WHERE lower(name) like lower('%NAME%') or vector @@ " +
                "plainto_tsquery('NAME')) as l JOIN data.book_search bs on l.LID = bs.LID WHERE " +
                "year >= 0 and year <= 3000 and tag is null LIMIT 5 OFFSET 5";
        FindLitByLikeNameQuery noRatingSettings = new FindLitByLikeNameQuery(5, 5, "NAME");
        noRatingSettings.setYearStart(0);
        noRatingSettings.setMinRating(5.0);
        noRatingSettings.setOrderByRating(false);

        //Act
        String ratingQuery = requests.bookSearchQuery(ratingSettings);
        String noRatingQuery = requests.bookSearchQuery(noRatingSettings);

        //Assert
        assertEquals(withBestRatingType, ratingQuery);
        assertEquals(noRating, noRatingQuery);
    }

    @Test
    void anySearchQuery() {
        //Arrange
        PostgresLiteratureRequests requests = new PostgresLiteratureRequests();
        Vector<String> tags = new Vector<>();
        tags.add("TAG1");
        tags.add("TAG2");

        String withBestRatingType =  "SELECT lid, name, type, year, authors, min(rating) as rating FROM data.book_search " +
                "WHERE year >= 0 and year <= 3000 and type = 'TYPE' and (tag = 'TAG1' and rating >= 5.0 or " +
                "tag = 'TAG2' and rating >= 5.0) GROUP BY (lid, name, type, year, authors) HAVING count(*) = 2 " +
                "ORDER BY rating DESC LIMIT 5 OFFSET 5";
        FindLitByTagQuery ratingSettings = new FindLitByTagQuery(5, 5);
        ratingSettings.setOrderByRating(true);
        ratingSettings.setYearStart(0);
        ratingSettings.setMinRating(5.0);
        ratingSettings.setLitType("TYPE");
        ratingSettings.setTags(tags);

        String noRating = "SELECT lid, name, type, year, authors, rating as rating FROM data.book_search WHERE " +
                "year >= 0 and year <= 3000 and tag is null LIMIT 5 OFFSET 5";
        FindLitByTagQuery noRatingSettings = new FindLitByTagQuery(5, 5);
        noRatingSettings.setOrderByRating(false);
        noRatingSettings.setYearStart(0);

        //Act
        String ratingQuery = requests.anySearchQuery(ratingSettings);
        String noRatingQuery = requests.anySearchQuery(noRatingSettings);

        //Assert
        assertEquals(withBestRatingType, ratingQuery);
        assertEquals(noRating, noRatingQuery);
    }

    @Test
    void termSearchQuery() {
        //Arrange
        PostgresLiteratureRequests requests = new PostgresLiteratureRequests();

        FindLitByLikeTermNameQuery recentlySettings = new FindLitByLikeTermNameQuery
                (5, 5, "NAME");
        recentlySettings.setOrderByRating(false);
        recentlySettings.setRecentlyAdded(true);
        recentlySettings.setMinRating(3.0);
        recentlySettings.setLitType("TYPE");
        recentlySettings.setYearStart(0);
        recentlySettings.setYearEnd(3000);
        String recentlyWithTypeExpected = "with terms(tid) as (SELECT tid FROM data.terms WHERE lower(name) = lower('NAME') " +
                "or vector @@ plainto_tsquery('NAME')), term_lit (LID, rating, rates_amount) as " +
                "(SELECT LID, rating, rates_amount FROM (SELECT tl.LID, rating, rates_amount, " +
                "row_number() over (partition by LID order by rating DESC) as rn FROM terms t " +
                "JOIN data.terms_lit tl on tl.tid = t.tid and tl.rating >= 3.0) as temp WHERE rn = 1) " +
                "SELECT l.lid, l.name, l.type, l.year, l.authors, tl.rating, tl.rates_amount FROM " +
                "term_lit tl JOIN data.lit l on l.LID = tl.LID WHERE year >= 0 and year <= 3000 and type = 'TYPE' " +
                "ORDER BY tl.rates_amount LIMIT 5 OFFSET 5";

        FindLitByLikeTermNameQuery bestSettings = new FindLitByLikeTermNameQuery
                (5, 5, "NAME");
        bestSettings.setOrderByRating(true);
        bestSettings.setRecentlyAdded(false);
        bestSettings.setMinRating(3.0);
        bestSettings.setYearStart(0);
        bestSettings.setYearEnd(3000);
        String bestExpected = "with terms(tid) as (SELECT tid FROM data.terms WHERE lower(name) = lower('NAME') " +
                "or vector @@ plainto_tsquery('NAME')), term_lit (LID, rating, rates_amount) as " +
                "(SELECT LID, rating, rates_amount FROM (SELECT tl.LID, rating, rates_amount, " +
                "row_number() over (partition by LID order by rating DESC) as rn FROM terms t " +
                "JOIN data.terms_lit tl on tl.tid = t.tid and tl.rating >= 3.0) as temp WHERE rn = 1) " +
                "SELECT l.lid, l.name, l.type, l.year, l.authors, tl.rating, tl.rates_amount FROM " +
                "term_lit tl JOIN data.lit l on l.LID = tl.LID WHERE year >= 0 and year <= 3000 " +
                "ORDER BY tl.rating DESC LIMIT 5 OFFSET 5";

        FindLitByLikeTermNameQuery bothSettings = new FindLitByLikeTermNameQuery
                (5, 5, "NAME");
        bothSettings.setOrderByRating(true);
        bothSettings.setRecentlyAdded(true);
        bothSettings.setMinRating(3.0);
        bothSettings.setYearStart(0);
        bothSettings.setYearEnd(3000);
        String bothExpected = "with terms(tid) as (SELECT tid FROM data.terms WHERE lower(name) = lower('NAME') " +
                "or vector @@ plainto_tsquery('NAME')), term_lit (LID, rating, rates_amount) as " +
                "(SELECT LID, rating, rates_amount FROM (SELECT tl.LID, rating, rates_amount, " +
                "row_number() over (partition by LID order by rating DESC) as rn FROM terms t " +
                "JOIN data.terms_lit tl on tl.tid = t.tid and tl.rating >= 3.0) as temp WHERE rn = 1) " +
                "SELECT l.lid, l.name, l.type, l.year, l.authors, tl.rating, tl.rates_amount FROM " +
                "term_lit tl JOIN data.lit l on l.LID = tl.LID WHERE year >= 0 and year <= 3000 " +
                "ORDER BY tl.rates_amount, tl.rating DESC LIMIT 5 OFFSET 5";

        //Act
        String recentlyWithTypeQuery = requests.termSearchQuery(recentlySettings);
        String bestQuery = requests.termSearchQuery(bestSettings);
        String bothQuery = requests.termSearchQuery(bothSettings);

        //Assert
        assertEquals(recentlyWithTypeExpected, recentlyWithTypeQuery);
        assertEquals(bestExpected, bestQuery);
        assertEquals(bothExpected, bothQuery);
    }

    @Test
    void termIdSearchQueryTest() {

        //Arrange
        PostgresLiteratureRequests requests = new PostgresLiteratureRequests();

        FindLitByTermIdQuery bestSettings = new FindLitByTermIdQuery
                (5, 6, 7);
        bestSettings.setOrderByRating(true);
        bestSettings.setRecentlyAdded(false);
        bestSettings.setMinRating(3.0);

        String bestWithTypeExpected = "SELECT l.lid, l.name, l.type, l.year, l.authors, tl.rating, tl.rates_amount " +
                "FROM data.terms_lit tl JOIN data.lit l on tl.lid = l.lid and tl.tid = 7 and tl.rating >= 3.0 and " +
                "year >= -3000 and year <= 3000 ORDER BY tl.rating DESC LIMIT 5 OFFSET 6";

        FindLitByTermIdQuery recentlySettings = new FindLitByTermIdQuery
                (5, 6, 7);
        recentlySettings.setOrderByRating(false);
        recentlySettings.setRecentlyAdded(true);
        recentlySettings.setMinRating(3.0);
        String recentlyExpected = "SELECT l.lid, l.name, l.type, l.year, l.authors, tl.rating, tl.rates_amount " +
                "FROM data.terms_lit tl JOIN data.lit l on tl.lid = l.lid and tl.tid = 7 and tl.rating >= 3.0 and " +
                "year >= -3000 and year <= 3000 ORDER BY tl.rates_amount LIMIT 5 OFFSET 6";

        FindLitByTermIdQuery bothSettings = new FindLitByTermIdQuery
                (5, 6, 7);
        bothSettings.setOrderByRating(true);
        bothSettings.setRecentlyAdded(true);
        bothSettings.setMinRating(3.0);

        String bothExpected = "SELECT l.lid, l.name, l.type, l.year, l.authors, tl.rating, tl.rates_amount " +
                "FROM data.terms_lit tl JOIN data.lit l on tl.lid = l.lid and tl.tid = 7 and tl.rating >= 3.0 and " +
                "year >= -3000 and year <= 3000 ORDER BY tl.rates_amount, tl.rating DESC LIMIT 5 OFFSET 6";

        //Act
        String bestWithTypeQuery = requests.termIdSearchQuery(bestSettings);
        String recentlyQuery = requests.termIdSearchQuery(recentlySettings);
        String bothQuery = requests.termIdSearchQuery(bothSettings);

        //Assert
        assertEquals(bestWithTypeExpected, bestWithTypeQuery);
        assertEquals(recentlyExpected, recentlyQuery);
        assertEquals(bothExpected, bothQuery);
    }

    @Test
    void userTermLitRatingQuery() {
        //Arrange
        UserTermLitRatingQuery settings = new UserTermLitRatingQuery(0, 2, 1);
        String expect = "SELECT lid, rating FROM data.term_lit_rates WHERE uid = 0 and lid = 1 and tid = 2";
        PostgresLiteratureRequests builder = new PostgresLiteratureRequests();

        //Act
        String query = builder.userTermLitRatingQuery(settings);

        //Assert
        assertEquals(expect, query);
    }
}