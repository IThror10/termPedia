package com.TermPedia.events.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.events.BaseEvent;
import com.TermPedia.events.result.EventStatus;
import com.TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;

public class LogoutEvent extends BaseEvent {
    private EventStatus status;
    public final Integer userId;

    public LogoutEvent(Integer userId) {
        super(null);
        this.userId = userId;
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitLogoutEvent(this);
    }

    @Override
    public EventStatus getResult() {
        return status;
    }

    public void setResult(EventStatus status) {
        this.status = status;
    }
}
