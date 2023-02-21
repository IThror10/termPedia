package com.TermPedia.factory;

import com.TermPedia.factory.command.postgres.PostgresCommandConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresTestCommandConnection extends PostgresCommandConnection {
    @Override
    public Connection establishEventHandlerConnection() throws Exception {
        return DriverManager.getConnection(
                postgresUrl,
                "command_test_authorized",
                "write_only"
        );
    }

    @Override
    public Connection establishReqAuthHandlerConnection() throws Exception {
        return DriverManager.getConnection(
                postgresUrl,
                "command_test_access",
                "read_only"
        );
    }

    @Override
    public Connection establishSyncConnection() throws Exception {
        return DriverManager.getConnection(
                postgresUrl,
                "command_test_synchronizer",
                "sync"
        );
    }
}
