package com.TermPedia.factory.command;


import com.TermPedia.commands.result.EventResult;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.factory.adapters.ISearchAdapter;
import com.TermPedia.factory.command.common.IEventHandlerRequests;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class StatementEventHandler implements EventHandler {
    private final ISearchAdapter searcher;
    private final IEventHandlerRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("CommandDB"); }

    public StatementEventHandler(@NotNull ISearchAdapter searcher, @NotNull IEventHandlerRequests builder) {
        this.searcher = searcher;
        this.builder = builder;
    }

    @Override
    public EventResult accept(@NotNull DataEvent event) throws ActionsException {
        if (event.getUid() == null)
            throw new ActionsException("You need to be logged in");

        String query = builder.acceptEventQuery(event);
        try {
            searcher.execute(query);
            searcher.next();
            return new EventResult(searcher.getString("accept_event"));
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }
}
