package com.TermPedia.events.result;

import com.TermPedia.events.result.EventResult;

public class EventStatus extends EventResult {
    private final boolean status;
    public EventStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
