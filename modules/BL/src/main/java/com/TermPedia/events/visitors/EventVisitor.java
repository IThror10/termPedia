package com.TermPedia.events.visitors;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.events.data.*;
import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.LogoutEvent;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.events.user.ValidateEvent;

public interface EventVisitor {
    void visitRegisterEvent(RegisterEvent event) throws ActionsException;
    void visitAuthorizeEvent(AuthorizeEvent event) throws ActionsException;
    void visitValidateEvent(ValidateEvent event) throws ActionsException;
    void visitLogoutEvent(LogoutEvent event) throws ActionsException;

    void visitAddLitToTermEvent(AddLitToTermEvent event) throws ActionsException;
    void visitAddTagToTermEvent(AddTagToTermEvent event) throws ActionsException;
    void visitAddTermEvent(AddTermEvent event) throws ActionsException;
    void visitChangeTermLitRatingEvent(ChangeTermLitRatingEvent event) throws ActionsException;
    void visitChangeTermTagRatingEvent(ChangeTermTagRatingEvent event) throws ActionsException;
}
