package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.BaseProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;

public abstract class SyncQueryFactory extends QueryFactory {
    public abstract IUpdater createUpdater() throws ActionsException;
    private static SyncQueryFactory factory = null;

    public static SyncQueryFactory instance() throws NullPointerException {
        if (factory == null) {
            BaseProvider _provider = QueryFactory._provider;
            if (_provider == null)
                throw new NullPointerException("Provider Is Not Set");

            factory = table.get(_provider.getKey("QueryFactory"));
            if (factory == null)
                throw new NullPointerException("QueryFactory not found");
        }
        return factory;
    }

    private static Hashtable<String, SyncQueryFactory> table;
    static { table = new Hashtable<>(5); }
    protected static void register(@NotNull String name, @NotNull SyncQueryFactory factory) {
        QueryFactory.register(name, factory);
        table.put(name, factory);
    }

    public static void setProvider(BaseProvider provider) {
        QueryFactory.setProvider(provider);
    }
}
