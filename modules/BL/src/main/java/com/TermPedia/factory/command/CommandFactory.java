package com.TermPedia.factory.command;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.provider.BaseProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;

public abstract class CommandFactory {
    public abstract EventHandler createEventHandler() throws ActionsException;
    public abstract UserCommandHandler createReqAuthHandler() throws ActionsException;
    private static CommandFactory factory = null;
    public static CommandFactory instance() throws NullPointerException {
        if (factory == null) {
            if (_provider == null)
                throw new NullPointerException("Provider Is Not Set");

            factory = table.get(_provider.getKey("CommandFactory"));
            if (factory == null)
                throw new NullPointerException("CommandFactory not found");
        }
        return factory;
    }

    protected final static Hashtable<String, CommandFactory> table;
    static { table = new Hashtable<>(5); }

    protected static void register(@NotNull String name, @NotNull CommandFactory factory) {
        table.put(name, factory);
    }

    protected static BaseProvider _provider = null;
    public static void setProvider(BaseProvider provider) { _provider = provider; }
}
