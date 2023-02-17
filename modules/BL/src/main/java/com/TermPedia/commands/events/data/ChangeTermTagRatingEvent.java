package com.TermPedia.commands.events.data;

import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.commands.events.EventType;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.commands.CommandVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;
import org.jetbrains.annotations.Nullable;

public class ChangeTermTagRatingEvent extends DataEvent {
    public ChangeTermTagRatingEvent(
            @Nullable Integer userId,
            @NotNull Integer termId,
            @NotNull String tag,
            int mark
    ) throws FormatException {
        super(userId, EventType.change_term_tag_rating);
        if (termId == null || tag == null)
            throw new FormatException("Term or Tag not provided");
        else if (mark < 0 || mark > 5)
            throw new FormatException("Wrong mark");
        else {
            this.data = new JsonBuilder(128)
                    .addInt("TID", termId)
                    .addStr("Tag", tag)
                    .addInt("Mark", mark)
                    .getData();
        }
    }

    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitChangeTermTagRatingEvent(this);
    }
}
