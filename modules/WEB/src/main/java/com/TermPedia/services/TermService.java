package com.TermPedia.services;

import com.TermPedia.commands.events.data.*;
import com.TermPedia.handlers.CommandHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.lit.FindLitByTermIdQuery;
import com.TermPedia.queries.tags.FindTagByTermIdQuery;
import com.TermPedia.queries.terms.FindTermByNameQuery;
import com.TermPedia.responses.item.RatedTagResponse;
import com.TermPedia.responses.lit.RatedLiteratureResponse;
import com.TermPedia.responses.term.TermsResponse;
import org.springframework.stereotype.Service;

@Service
public class TermService {

    public void addLitToTerm(AddLitToTermEvent event) {
        CommandHandler handler = new CommandHandler();
        handler.handle(event);
    }

    public void addTagToTerm(AddTagToTermEvent event) {
        CommandHandler handler = new CommandHandler();
        handler.handle(event);
    }

    public void addTerm(AddTermEvent event) {
        CommandHandler handler = new CommandHandler();
        handler.handle(event);
    }

    public TermsResponse getTerms(FindTermByNameQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        return new TermsResponse(query.getResult().getTerms());
    }

    public RatedTagResponse getTagsByTerm(FindTagByTermIdQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        return new RatedTagResponse(query.getResult().getTags());
    }

    public RatedLiteratureResponse getLitByTerm(FindLitByTermIdQuery query) {
        QueryHandler handler = new QueryHandler();
        handler.handle(query);

        return new RatedLiteratureResponse(query.getResult().getBooks());
    }
}