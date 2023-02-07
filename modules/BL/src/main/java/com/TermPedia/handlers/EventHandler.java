package com.TermPedia.handlers;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.events.BaseEvent;
import com.TermPedia.events.visitors.EventHandlerVisitor;

public class EventHandler {
    public void handle(BaseEvent event) throws ActionsException {
        EventHandlerVisitor visitor = new EventHandlerVisitor();
        event.acceptVisitor(visitor);
    }
}
