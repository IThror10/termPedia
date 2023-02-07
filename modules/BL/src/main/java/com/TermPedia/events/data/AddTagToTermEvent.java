package com.TermPedia.events.data;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.events.EventType;
import com.TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;

public class AddTagToTermEvent extends DataEvent {
    public AddTagToTermEvent(@NotNull String term, @NotNull String tag, Integer uid) throws ActionsException {
        super(uid);
        this.eventType = EventType.new_tag;

        if (tag.length() < 2)
            throw new ActionsException("Tag name is too short");

        this.data = new JsonBuilder(128)
                .addStr("Term", term)
                .addStr("Tag", tag)
                .getData();
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitAddTagToTermEvent(this);
    }
}
