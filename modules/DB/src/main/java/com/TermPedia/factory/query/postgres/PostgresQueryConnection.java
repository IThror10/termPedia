package com.TermPedia.factory.query.postgres;

import com.TermPedia.factory.provider.EnvProvider;
import com.TermPedia.factory.query.common.IQueryConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresQueryConnection implements IQueryConnection {
    protected final String postgresUrl;
    public PostgresQueryConnection() {
        EnvProvider provider = new EnvProvider();
        String host, port, db;
        if ((host = provider.getKey("QUERY_HOST")) == null)
            throw new NullPointerException("HOST IS NOT PROVIDED");
        else if ((port = provider.getKey("QUERY_PORT")) == null)
            throw new NullPointerException("PORT IS NOT PROVIDED");
        else if ((db = provider.getKey("QUERY_DB")) == null)
            throw new NullPointerException("POSTGRES QUERY DB NAME IS NOT PROVIDED");
        else
            postgresUrl = "jdbc:postgresql://" + host + ":" + port + "/" + db;
    }
    @Override
    public Connection establishReaderConnection() throws Exception {
        return DriverManager.getConnection(
                postgresUrl,
                "query_reader",
                "read_only"
        );
    }

    @Override
    public Connection establishUpdaterConnection() throws Exception {
        return DriverManager.getConnection(
                postgresUrl,
                "query_synchronizer",
                "synch"
        );
    }
}
