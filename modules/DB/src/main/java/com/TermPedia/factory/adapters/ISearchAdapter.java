package com.TermPedia.factory.adapters;

import java.util.List;

public interface ISearchAdapter {
    boolean execute(String query) throws Exception;
    boolean next() throws Exception;
    int getInt(String key) throws Exception;
    String getString(String key) throws Exception;
    boolean getBoolean(String key) throws Exception;
    double getDouble(String key) throws Exception;
    List<String> getStringArray(String key) throws Exception;
    void closeConnection();
}
