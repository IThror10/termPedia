package com.TermPedia.factory.command;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.events.data.DataEvent;
import com.TermPedia.events.result.EventStatus;
import org.jetbrains.annotations.NotNull;

public interface EventHandler {
    EventStatus accept(@NotNull DataEvent event) throws ActionsException;
}
