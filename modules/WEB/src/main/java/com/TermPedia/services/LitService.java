package com.TermPedia.services;

import com.TermPedia.handlers.*;
import com.TermPedia.dto.users.UserRating;
import com.TermPedia.commands.events.data.*;

import com.TermPedia.queries.lit.*;
import com.TermPedia.queries.user.UserTermLitRatingQuery;
import com.TermPedia.queries.authors.FindAuthorByNameQuery;
import com.TermPedia.queries.litTypes.FindLitTypesByNameQuery;

import com.TermPedia.responses.lit.*;
import com.TermPedia.responses.item.*;
import com.TermPedia.responses.user.MarkResponse;
import org.springframework.stereotype.Service;

@Service
public class LitService {
    public void addLiterature(AddLitEvent event) {
        CommandHandler handler = new CommandHandler();
        handler.handle(event);
    }

    public void changeTagLitRating(ChangeTermLitRatingEvent event) {
        CommandHandler handler = new CommandHandler();
        handler.handle(event);
    }

    public MarkResponse getLitTermRating(UserTermLitRatingQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        UserRating rating = query.getResult().getRating();
        return new MarkResponse(rating.getTermId(), rating.getIdentifier(), rating.getUserRating());
    }

    public AuthorResponse getAuthors(FindAuthorByNameQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        return new AuthorResponse(query.getResult().getAuthors());
    }

    public LitTypesResponse getLitTypes(FindLitTypesByNameQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        return new LitTypesResponse(query.getResult().getLitTypes());
    }

    public TagLiteratureResponse searchByTags(FindLitByTagQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        return new TagLiteratureResponse(query.getResult().getBooks());
    }

    public RatedLiteratureResponse searchByTerm(FindLitByLikeTermNameQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        return new RatedLiteratureResponse(query.getResult().getBooks());
    }

    public LiteratureResponse searchByAuthor(FindLitByAuthorNameQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        return new LiteratureResponse(query.getResult().getBooks());
    }

    public TagLiteratureResponse searchByName(FindLitByLikeNameQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        return new TagLiteratureResponse(query.getResult().getBooks());
    }

    public RatedLiteratureResponse searchByTermId(FindLitByTermIdQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        return new RatedLiteratureResponse(query.getResult().getBooks());
    }

    public void addLitToTerm(AddLitToTermEvent event) {
        CommandHandler handler = new CommandHandler();
        handler.handle(event);
    }
}
