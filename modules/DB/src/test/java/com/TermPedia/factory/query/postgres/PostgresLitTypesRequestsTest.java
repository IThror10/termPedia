package com.TermPedia.factory.query.postgres;

import com.TermPedia.queries.litTypes.FindLitTypesByNameQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresLitTypesRequestsTest {

    @Test
    void getLitTypesByNameQuery() throws Exception {
        //Arrange
        PostgresLitTypesRequests requests = new PostgresLitTypesRequests();
        FindLitTypesByNameQuery settings = new FindLitTypesByNameQuery(10, 7, "NAME");

        String expected = "SELECT name FROM data.lit_types WHERE lower(name) = lower('NAME') or " +
                "plainto_tsquery('NAME') @@ vector ORDER BY name LIMIT 10 OFFSET 7";

        //Act
        String query = requests.getLitTypesByNameQuery(settings);

        //Assert
        assertEquals(expected, query);
    }
}