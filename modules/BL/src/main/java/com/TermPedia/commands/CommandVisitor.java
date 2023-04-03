package com.TermPedia.commands;

import com.TermPedia.commands.events.data.*;
import com.TermPedia.commands.user.AuthorizeCommand;
import com.TermPedia.commands.user.ChangeContactsCommand;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.commands.user.*;

public interface CommandVisitor {
    void visitRegisterEvent(RegisterEvent event) throws ActionsException;
    void visitAuthorizeCommand(AuthorizeCommand event) throws ActionsException;
    void visitValidateCommand(ValidateCommand event) throws ActionsException;
    void visitLogoutCommand(LogoutCommand event) throws ActionsException;
    void visitChangeUserContactsCommand(ChangeContactsCommand event) throws ActionsException;

    void visitAddTermEvent(AddTermEvent event) throws ActionsException;
    void visitAddLitEvent(AddLitEvent event) throws ActionsException;
    void visitAddLitToTermEvent(AddLitToTermEvent event) throws ActionsException;
    void visitAddTagToTermEvent(AddTagToTermEvent event) throws ActionsException;
    void visitChangeTermLitRatingEvent(ChangeTermLitRatingEvent event) throws ActionsException;
    void visitChangeTermTagRatingEvent(ChangeTermTagRatingEvent event) throws ActionsException;
}
