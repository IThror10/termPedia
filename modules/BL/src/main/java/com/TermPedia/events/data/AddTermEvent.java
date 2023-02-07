package com.TermPedia.events.data;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.Term;
import com.TermPedia.events.EventType;
import com.TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;

public class AddTermEvent extends DataEvent {
    public final Term term;
    public AddTermEvent(@NotNull String term, @NotNull String description, Integer uid) throws ActionsException {
        super(uid);
        this.eventType = EventType.new_term;

        if (term.length() < 2)
            throw new ActionsException("Term name is too short");
        else if (description.length() < 10)
            throw new ActionsException("Description is too short");
        else {
            this.term = new Term(term, description);
            this.data = new JsonBuilder(128)
                    .addStr("Term", term)
                    .addStr("Description", description)
                    .getData();
        }
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitAddTermEvent(this);
    }
}
