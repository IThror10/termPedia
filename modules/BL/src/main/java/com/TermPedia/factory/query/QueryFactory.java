package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.provider.BaseProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;

public abstract class QueryFactory {
    public abstract TermsSearcher createTermSearcher() throws ActionsException;
    public abstract TagsSearcher createTagSearcher() throws ActionsException;
    public abstract AuthorsSearcher createAuthorSearcher() throws ActionsException;
    public abstract LitTypesSearcher createLitTypesSearcher() throws ActionsException;
    public abstract LiteratureSearcher createBookSearcher() throws ActionsException;

    private static QueryFactory factory = null;
    public static QueryFactory instance() throws NullPointerException {
        if (factory == null) {
            if (_provider == null)
                throw new NullPointerException("Provider Is Not Set");

            factory = table.get(_provider.getKey("QueryFactory"));
            if (factory == null)
                throw new NullPointerException("CommandFactory not found");
        }
        return factory;
    }

    protected final static Hashtable<String, QueryFactory> table;
    static {
        table = new Hashtable<>(5);
    }
    protected static void register(@NotNull String name, @NotNull QueryFactory factory) {
        table.put(name, factory);
    }

    protected static BaseProvider _provider = null;
    public static void setProvider(BaseProvider provider) { _provider = provider; }
}