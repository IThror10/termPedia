package com.TermPedia.events.data;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.events.EventType;
import com.TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.Book;
import com.TermPedia.dto.JsonBuilder;

public class AddLitToTermEvent extends DataEvent {
    public AddLitToTermEvent(@NotNull String term, @NotNull Book book, Integer uid) throws ActionsException {
        super(uid);
        this.eventType = EventType.new_lit;

        this.data = new JsonBuilder(128)
                .addStr("Term", term)
                .addBook(book)
                .getData();
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitAddLitToTermEvent(this);
    }
}
