package com.TermPedia.handlers;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.commands.BaseCommand;

public class CommandHandler {
    public void handle(BaseCommand event) throws ActionsException {
        CommandHandlerVisitor visitor = new CommandHandlerVisitor();
        event.acceptVisitor(visitor);
    }
}
