package com.TermPedia.events.visitors;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.events.data.*;
import com.TermPedia.events.result.AuthEventResult;
import com.TermPedia.events.result.EventStatus;
import com.TermPedia.events.result.ValidateEventResult;
import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.LogoutEvent;
import com.TermPedia.events.user.ValidateEvent;
import com.TermPedia.factory.query.QueryFactory;
import com.TermPedia.factory.query.TermsSearcher;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.factory.command.CommandFactory;
import com.TermPedia.factory.command.EventHandler;
import com.TermPedia.factory.command.ReqAuthHandler;


public class EventHandlerVisitor implements EventVisitor {
    @Override
    public void visitRegisterEvent(RegisterEvent event) throws ActionsException {
        ReqAuthHandler handler = CommandFactory.instance().createReqAuthHandler();
        AuthEventResult result = handler.register(event);
        event.setResult(result);
    }

    @Override
    public void visitAuthorizeEvent(AuthorizeEvent event) throws ActionsException {
        ReqAuthHandler handler = CommandFactory.instance().createReqAuthHandler();
        AuthEventResult result = handler.authorize(event);
        event.setResult(result);
    }

    @Override
    public void visitValidateEvent(ValidateEvent event) throws ActionsException {
        ReqAuthHandler handler = CommandFactory.instance().createReqAuthHandler();
        ValidateEventResult result = handler.validate(event);
        event.setResult(result);
    }

    @Override
    public void visitLogoutEvent(LogoutEvent event) throws ActionsException {
        ReqAuthHandler handler = CommandFactory.instance().createReqAuthHandler();
        EventStatus status = handler.logout(event);
        event.setResult(status);
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
        TermsSearcher searcher = QueryFactory.instance().createTermSearcher();
        if (searcher.termExists(event.term))
            throw new ActionsException("Term is Already Exists");

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
        EventStatus status = handler.accept(event);
        event.setResult(status);
    }
}
