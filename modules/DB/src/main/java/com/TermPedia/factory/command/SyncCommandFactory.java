package com.TermPedia.factory.command;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.provider.BaseProvider;
import com.TermPedia.factory.command.common.ISynchronizer;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;

public abstract class SyncCommandFactory extends CommandFactory {
    public abstract ISynchronizer createSynchronizer() throws ActionsException;
    private static SyncCommandFactory factory = null;

    public static SyncCommandFactory instance() throws NullPointerException {
        if (factory == null) {
            BaseProvider _provider = CommandFactory._provider;
            if (_provider == null)
                throw new NullPointerException("Provider Is Not Set");

            factory = table.get(_provider.getKey("CommandFactory"));
            if (factory == null)
                throw new NullPointerException("CommandFactory not found");
        }
        return SyncCommandFactory.factory;
    }

    private final static Hashtable<String, SyncCommandFactory> table;
    static { table = new Hashtable<>(5); }

    protected static void register(@NotNull String name, @NotNull SyncCommandFactory factory) {
        CommandFactory.register(name, factory);
        table.put(name, factory);
    }

    public static void setProvider(BaseProvider provider) {
        CommandFactory.setProvider(provider);
    }
}
