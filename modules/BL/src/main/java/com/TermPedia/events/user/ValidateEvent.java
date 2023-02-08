package com.TermPedia.events.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.events.BaseEvent;
import com.TermPedia.events.result.ValidateEventResult;
import com.TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;

public class ValidateEvent extends BaseEvent {
    private ValidateEventResult result;
    public final String secret;
    public final String login;

    public ValidateEvent(String login, String secret) {
        super(null);
        this.secret = secret;
        this.login = login;
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitValidateEvent(this);
    }

    @Override
    public ValidateEventResult getResult() {
        return result;
    }

    public void setResult(ValidateEventResult result) {
        this.result = result;
    }
}
