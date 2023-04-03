package com.TermPedia.factory.command.postgres;

import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.factory.command.common.IEventHandlerRequests;

public class PostgresEventHandlerRequests implements IEventHandlerRequests {
    private final StringBuilder builder;
    public PostgresEventHandlerRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String acceptEventQuery(DataEvent event) {
        builder.setLength(0);
        builder.append("SELECT * FROM app.accept_event(");
        builder.append(event.getUid());
        builder.append(", '");
        builder.append(event.getDateTime());
        builder.append("', ");
        builder.append(event.getEventType().ordinal());
        builder.append(", '");
        builder.append(event.getData());
        builder.append("');");
        return builder.toString();
    }
}
