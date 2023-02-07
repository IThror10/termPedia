package com.TermPedia.events.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.users.User;
import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.events.BaseEvent;
import com.TermPedia.events.EventType;
import com.TermPedia.events.result.AuthEventResult;
import com.TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;

public class AuthorizeEvent extends BaseEvent {
    private AuthEventResult result;

    public AuthorizeEvent(@NotNull String login, @NotNull String password) throws ActionsException {
        super(0);
        this.result = null;
        this.eventType = EventType.authorization;

        this.data = new JsonBuilder(128)
                .addStr("Login", login)
                .addStr("Password", password)
                .getData();
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitAuthorizeEvent(this);
    }

    @Override
    public AuthEventResult getResult() {
        return result;
    }

    public void setResult(AuthEventResult result) {
        this.result = result;
    }
}