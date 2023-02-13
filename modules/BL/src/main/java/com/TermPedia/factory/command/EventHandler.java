package com.TermPedia.factory.command;

import com.TermPedia.commands.result.EventResult;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.commands.events.DataEvent;
import org.jetbrains.annotations.NotNull;

public interface EventHandler {
    EventResult accept(@NotNull DataEvent event) throws ActionsException;
}
