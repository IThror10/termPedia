package com.TermPedia.commands.events;

import com.TermPedia.commands.BaseCommand;
import com.TermPedia.commands.result.EventResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DataEvent extends BaseCommand {
    private EventResult result;
    private final EventType eventType;
    protected DataEvent(Integer uid, EventType eventType) {
        super(uid);
        this.eventType = eventType;
        this.result = null;
    }
}
