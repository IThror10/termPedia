package com.TermPedia.handlers;

import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.commands.events.data.*;
import com.TermPedia.commands.result.EventResult;
import com.TermPedia.commands.user.AuthorizeCommand;
import com.TermPedia.commands.user.ChangeContactsCommand;
import com.TermPedia.commands.CommandVisitor;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.commands.result.AuthCommandResult;
import com.TermPedia.commands.result.ContactDataEventResult;
import com.TermPedia.commands.result.ValidateCommandResult;
import com.TermPedia.commands.user.*;
import com.TermPedia.factory.command.CommandFactory;
import com.TermPedia.factory.command.EventHandler;
import com.TermPedia.factory.command.UserCommandHandler;


public class CommandHandlerVisitor implements CommandVisitor {
    @Override
    public void visitRegisterEvent(RegisterEvent event) throws ActionsException {
        UserCommandHandler handler = CommandFactory.instance().createReqAuthHandler();
        EventResult result = handler.register(event);
        event.setResult(result);
    }

    @Override
    public void visitAuthorizeCommand(AuthorizeCommand event) throws ActionsException {
        UserCommandHandler handler = CommandFactory.instance().createReqAuthHandler();
        AuthCommandResult result = handler.authorize(event);
        event.setResult(result);
    }

    @Override
    public void visitValidateCommand(ValidateCommand event) throws ActionsException {
        UserCommandHandler handler = CommandFactory.instance().createReqAuthHandler();
        ValidateCommandResult result = handler.validate(event);
        event.setResult(result);
    }

    @Override
    public void visitLogoutCommand(LogoutCommand event) throws ActionsException {
        UserCommandHandler handler = CommandFactory.instance().createReqAuthHandler();
        EventResult result = handler.logout(event);
        event.setStatus(result);
    }

    @Override
    public void visitChangeUserContactsCommand(ChangeContactsCommand event) throws ActionsException {
        UserCommandHandler handler = CommandFactory.instance().createReqAuthHandler();
        ContactDataEventResult result = handler.changeContactData(event);
        event.setResult(result);
    }

    @Override
    public void visitAddLitToTermEvent(AddLitToTermEvent event) throws ActionsException {
        handleDataEvent(event);
    }

    @Override
    public void visitAddTagToTermEvent(AddTagToTermEvent event) throws ActionsException {
        handleDataEvent(event);
    }

    @Override
    public void visitAddTermEvent(AddTermEvent event) throws ActionsException {
        handleDataEvent(event);
    }

    @Override
    public void visitAddLitEvent(AddLitEvent event) throws ActionsException {
        handleDataEvent(event);
    }

    @Override
    public void visitChangeTermLitRatingEvent(ChangeTermLitRatingEvent event) throws ActionsException {
        handleDataEvent(event);
    }

    @Override
    public void visitChangeTermTagRatingEvent(ChangeTermTagRatingEvent event) throws ActionsException {
        handleDataEvent(event);
    }

    private void handleDataEvent(DataEvent event) throws ActionsException {
        EventHandler handler = CommandFactory.instance().createEventHandler();
        EventResult result = handler.accept(event);
        event.setResult(result);
    }
}
