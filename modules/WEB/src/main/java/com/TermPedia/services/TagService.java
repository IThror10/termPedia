package com.TermPedia.services;

import com.TermPedia.commands.events.data.ChangeTermTagRatingEvent;
import com.TermPedia.dto.users.UserRating;
import com.TermPedia.handlers.CommandHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.user.UserTermTagRatingQuery;
import com.TermPedia.responses.entity.TagResponse;
import com.TermPedia.responses.user.MarkResponse;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    public TagResponse findTagsByName(FindTagByNameQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);
        return new TagResponse(query.getResult().getTags());
    }

    public void changeTagTermRating(ChangeTermTagRatingEvent event) {
        CommandHandler handler = new CommandHandler();
        handler.handle(event);
    }

    public MarkResponse getTagTermRating(UserTermTagRatingQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        UserRating rating = query.getResult().getRating();
        return new MarkResponse(rating.getTermId(), rating.getIdentifier(), rating.getUserRating());
    }
}
