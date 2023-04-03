package com.TermPedia.commands.events.data;

import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.commands.events.EventType;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.commands.CommandVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;
import org.jetbrains.annotations.Nullable;

public class ChangeTermLitRatingEvent extends DataEvent {
    public ChangeTermLitRatingEvent(
            @Nullable Integer userId,
            @NotNull Integer termId,
            @NotNull Integer litId,
            int mark
    ) throws ActionsException {
        super(userId, EventType.change_term_lit_rating);

        if (termId == null || litId == null)
            throw new FormatException("Term or Literature not provided");
        else if (mark < 0 || mark > 5)
            throw new FormatException("Wrong mark");
        else {
            this.data = new JsonBuilder(128)
                    .addInt("TID", termId)
                    .addInt("LID", litId)
                    .addInt("Mark", mark)
                    .getData();
        }
    }

    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitChangeTermLitRatingEvent(this);
    }
}
