package com.TermPedia.commands.events.data;

import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.commands.events.EventType;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.commands.CommandVisitor;
import com.TermPedia.dto.tags.Tag;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;

public class AddTagToTermEvent extends DataEvent {
    public AddTagToTermEvent(
            @NotNull Integer termId,
            @NotNull Tag tag,
            Integer userId
    ) throws FormatException {
        super(userId, EventType.new_term_tag);
        if (tag == null || termId == null)
            throw new FormatException("Term or Tag not provided");
        else {
            this.data = new JsonBuilder(128)
                    .addInt("TID", termId)
                    .addStr("Tag", tag.getName())
                    .getData();
        }
    }

    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitAddTagToTermEvent(this);
    }
}
