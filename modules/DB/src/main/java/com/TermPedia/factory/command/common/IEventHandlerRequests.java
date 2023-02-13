package com.TermPedia.factory.command.common;

import com.TermPedia.commands.events.DataEvent;

public interface IEventHandlerRequests {
    String acceptEventQuery(DataEvent event);
}
