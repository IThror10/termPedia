package com.TermPedia.events.data;

import com.TermPedia.events.BaseEvent;
import com.TermPedia.events.result.EventStatus;

public abstract class DataEvent extends BaseEvent {
    private EventStatus status;
    protected DataEvent(Integer uid) {
        super(uid);
        this.status = null;
    }

    @Override
    public EventStatus getResult() { return this.status; }
    public void setResult(EventStatus status) { this.status = status; }
}
