package com.TermPedia.factory.query.postgres;

import com.TermPedia.queries.authors.FindAuthorByNameQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresAuthorsRequestsTest {

    @Test
    void getAuthorsByNameQuery() throws Exception {
        //Arrange
        PostgresAuthorsRequests requests = new PostgresAuthorsRequests();
        FindAuthorByNameQuery settings = new FindAuthorByNameQuery(5, 5, "NAME");

        String expected = "SELECT name FROM data.authors WHERE lower(name) like lower('%NAME%') " +
                "ORDER BY name LIMIT 5 OFFSET 5";

        //Act
        String query = requests.getAuthorsByNameQuery(settings);

        //Assert
        assertEquals(expected, query);
    }
}