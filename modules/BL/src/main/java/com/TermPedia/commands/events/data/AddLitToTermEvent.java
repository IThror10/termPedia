package com.TermPedia.commands.events.data;

import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.commands.events.EventType;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.commands.CommandVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;
import org.jetbrains.annotations.Nullable;

public class AddLitToTermEvent extends DataEvent {
    public AddLitToTermEvent(
            @NotNull Integer termId,
            @NotNull Integer litId,
            @Nullable Integer userId
    ) throws FormatException {
        super(userId, EventType.new_term_lit);
        if (termId == null || litId == null)
            throw new FormatException("Term or Literature ID not provided");
        else {
            this.data = new JsonBuilder(128)
                    .addInt("TID", termId)
                    .addInt("LID", litId)
                    .getData();
        }
    }

    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitAddLitToTermEvent(this);
    }
}
