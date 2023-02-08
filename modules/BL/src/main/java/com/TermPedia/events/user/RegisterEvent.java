package com.TermPedia.events.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.events.BaseEvent;
import com.TermPedia.events.result.AuthEventResult;
import com.TermPedia.events.result.EventResult;
import com.TermPedia.events.EventType;
import com.TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;

public class RegisterEvent extends BaseEvent {
    private AuthEventResult result;
    public RegisterEvent(@NotNull String login, @NotNull String password, @NotNull String email)
            throws ActionsException {
        super(null);
        this.result = null;

        if (login.length() < 5)
            throw new ActionsException("Логин слишком короткий");
        else if (password.length() < 5)
            throw new ActionsException("Пароль слишком короткий");

        this.eventType = EventType.registration;
        this.data = new JsonBuilder(128)
                .addStr("Login", login)
                .addStr("Password", password)
                .addStr("Email", email)
                .getData();
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitRegisterEvent(this);
    }

    @Override
    public AuthEventResult getResult() {
        return result;
    }

    public void setResult(AuthEventResult result) {
        this.result = result;
    }
}
