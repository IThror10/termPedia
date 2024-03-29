package com.TermPedia.factory.command.postgres;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.adapters.PostgresAdapter;
import com.TermPedia.factory.command.*;
import com.TermPedia.factory.command.common.ICommandConnection;
import com.TermPedia.factory.command.common.ISynchronizer;

import java.sql.Connection;
import java.util.logging.Logger;

public class PostgresCommandFactory extends SyncCommandFactory {
    private final static Logger logger;
    private static ICommandConnection _establisher;
    static {
        logger = Logger.getLogger("CommandDB");
        _establisher = null;
    }

    @Override
    public EventHandler createEventHandler() throws ActionsException {
        try {
            Connection connection = _establisher.establishEventHandlerConnection();
            return new StatementEventHandler(
                    new PostgresAdapter(connection),
                    new PostgresEventHandlerRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public UserCommandHandler createReqAuthHandler() throws ActionsException {
        try {
            Connection connection = _establisher.establishReqAuthHandlerConnection();
            return new StatementReqAuthHandler(
                    new PostgresAdapter(connection),
                    new PostgresReqAuthHandlerRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public ISynchronizer createSynchronizer() throws ActionsException {
        try {
            Connection connection = _establisher.establishSyncConnection();
            return new StatementSynchronizer(
                    new PostgresAdapter(connection, true),
                    new PostgresSyncRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    static public void completeRegistration() {
        SyncCommandFactory.register("postgres", new PostgresCommandFactory());
    }

    static public void setConnectionEstablisher(ICommandConnection establisher) { _establisher = establisher; }
}
