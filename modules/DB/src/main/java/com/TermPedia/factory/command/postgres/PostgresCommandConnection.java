package com.TermPedia.factory.command.postgres;

import com.TermPedia.factory.command.common.ICommandConnection;
import com.TermPedia.factory.provider.EnvProvider;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresCommandConnection implements ICommandConnection {
    protected final String postgresUrl;
    public PostgresCommandConnection() {
        EnvProvider provider = new EnvProvider();
        String host, port, db;
        if ((host = provider.getKey("COMMAND_HOST")) == null)
            throw new NullPointerException("HOST IS NOT PROVIDED");
        else if ((port = provider.getKey("COMMAND_PORT")) == null)
            throw new NullPointerException("PORT IS NOT PROVIDED");
        else if ((db = provider.getKey("COMMAND_DB")) == null)
            throw new NullPointerException("POSTGRES COMMAND DB NAME IS NOT PROVIDED");
        else
            postgresUrl = "jdbc:postgresql://" + host + ":" + port + "/" + db;
    }
    @Override
    public Connection establishEventHandlerConnection() throws Exception {
        return DriverManager.getConnection(
                postgresUrl,
                "command_authorized",
                "write_only"
        );
    }

    @Override
    public Connection establishReqAuthHandlerConnection() throws Exception {
        return DriverManager.getConnection(
                postgresUrl,
                "command_access",
                "read_only"
        );
    }

    @Override
    public Connection establishSyncConnection() throws Exception {
        return DriverManager.getConnection(
                postgresUrl,
                "command_synchronizer",
                "sync"
        );
    }
}
