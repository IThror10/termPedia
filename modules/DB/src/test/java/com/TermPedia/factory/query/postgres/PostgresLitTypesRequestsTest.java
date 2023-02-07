package com.TermPedia.factory.query.postgres;

import com.TermPedia.factory.query.postgres.PostgresLitTypesRequests;
import com.TermPedia.queries.instances.types.FindLitTypesByNameQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresLitTypesRequestsTest {

    @Test
    void getLitTypesByNameQuery() throws Exception {
        //Arrange
        PostgresLitTypesRequests requests = new PostgresLitTypesRequests();
        FindLitTypesByNameQuery settings = new FindLitTypesByNameQuery("NAME", 10, 7);

        String expected = "SELECT name FROM data.lit_types WHERE lower(name) = lower('NAME') or " +
                "plainto_tsquery('NAME') @@ vector ORDER BY name LIMIT 10 OFFSET 7";

        //Act
        String query = requests.getLitTypesByNameQuery(settings);

        //Assert
        assertEquals(expected, query);
    }
}