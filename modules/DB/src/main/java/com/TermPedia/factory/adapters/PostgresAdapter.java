package com.TermPedia.factory.adapters;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PostgresAdapter implements ISyncAdapter, ISearchAdapter{
    protected final Connection connection;
    protected ResultSet resultSet;
    private boolean updatable;

    public PostgresAdapter(Connection connection) {
        this.connection = connection;
        this.resultSet = null;
        this.updatable = false;
    }

    public PostgresAdapter(Connection connection, boolean updatable) {
        this.connection = connection;
        this.resultSet = null;
        this.updatable = updatable;
    }
    @Override
    public boolean execute(String query) throws Exception {
        Statement statement;
        if (updatable)
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        else
            statement = connection.createStatement();

        boolean result = statement.execute(query);
        resultSet = statement.getResultSet();
        return result;
    }

    @Override
    public boolean next() throws Exception {
        return resultSet.next();
    }

    @Override
    public int getInt(String key) throws Exception {
        return resultSet.getInt(key);
    }

    @Override
    public String getString(String key) throws Exception {
        return resultSet.getString(key);
    }

    @Override
    public boolean getBoolean(String key) throws Exception {
        return resultSet.getBoolean(key);
    }

    @Override
    public double getDouble(String key) throws Exception {
        return resultSet.getDouble(key);
    }

    @Override
    public List<String> getStringArray(String key) throws Exception {
        String source = resultSet.getString(key);
        List<String> list = new ArrayList<>();

        if (source == null)
            return list;

        int i = 0, j = 0;
        while (i >= 0 && j >= 0) {
            i = source.indexOf("\"", j + 1);
            j = source.indexOf("\"", i + 1);
            if (i > 0 && j > 0)
                list.add(source.substring(i + 1, j));
        }
        return list;
    }

    @Override
    public List<String> getJsonArray(String key) throws Exception {
        return null;
    }

    @Override
    public void updateBoolean(String key, boolean value) throws Exception {
        resultSet.updateBoolean(key, value);
        resultSet.updateRow();
    }
}
