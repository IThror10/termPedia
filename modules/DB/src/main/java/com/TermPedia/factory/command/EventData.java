package com.TermPedia.factory.command;

import com.TermPedia.commands.events.EventType;
import org.jetbrains.annotations.NotNull;

public class EventData {
    public final String json;
    public final EventType type;
    public final int uid;
    public EventData(@NotNull String json, int type, int uid) {
        this.json = json;
        this.type = EventType.values()[type];
        this.uid = uid;
    }
}
