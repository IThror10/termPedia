package com.TermPedia.commands.events.data;

import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.commands.events.EventType;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.term.Term;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.commands.CommandVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;
import org.jetbrains.annotations.Nullable;

public class AddTermEvent extends DataEvent {
    public AddTermEvent(
            @Nullable Integer userId,
            @NotNull Term term
    ) throws FormatException {
        super(userId, EventType.new_term);

        this.data = new JsonBuilder(128)
                .addStr("Term", term.getName())
                .addStr("Description", term.getDescription())
                .getData();
    }

    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitAddTermEvent(this);
    }
}
