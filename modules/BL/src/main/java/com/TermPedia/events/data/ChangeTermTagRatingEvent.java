package com.TermPedia.events.data;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.events.EventType;
import com.TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;

public class ChangeTermTagRatingEvent extends DataEvent {
    public ChangeTermTagRatingEvent(@NotNull String term, @NotNull String tag, int mark, Integer uid)
            throws ActionsException {
        super(uid);

        if (mark < 0 || mark > 5)
            throw new ActionsException("Wrong mark");

        this.eventType = EventType.change_term_tag_rating;
        this.data = new JsonBuilder(128)
                .addStr("Term", term)
                .addStr("Tag", tag)
                .addInt("Mark", mark)
                .getData();
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitChangeTermTagRatingEvent(this);
    }
}
