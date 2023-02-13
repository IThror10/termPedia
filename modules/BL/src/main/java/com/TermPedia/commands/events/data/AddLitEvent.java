package com.TermPedia.commands.events.data;

import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.commands.events.EventType;
import com.TermPedia.dto.JsonBuilder;
import com.TermPedia.dto.literature.Literature;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.commands.CommandVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AddLitEvent extends DataEvent {
    protected AddLitEvent(
            @Nullable Integer userId,
            @NotNull Literature literature
    ) throws FormatException {
        super(userId, EventType.new_lit);
        this.data = new JsonBuilder(256)
                .addLiterature(literature)
                .getData();
    }

    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitAddLitEvent(this);
    }
}
