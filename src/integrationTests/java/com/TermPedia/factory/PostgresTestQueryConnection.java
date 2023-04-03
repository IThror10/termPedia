package com.TermPedia.factory;

import com.TermPedia.factory.query.postgres.PostgresQueryConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresTestQueryConnection extends PostgresQueryConnection {
    @Override
    public Connection establishReaderConnection() throws Exception {
        return DriverManager.getConnection(
                postgresUrl,
                "query_test_reader",
                "read_only"
        );
    }

    @Override
    public Connection establishUpdaterConnection() throws Exception {
        return DriverManager.getConnection(
                postgresUrl,
                "query_test_synchronizer",
                "synch"
        );
    }
}
