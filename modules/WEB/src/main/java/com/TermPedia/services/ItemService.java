package com.TermPedia.services;

import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.authors.FindAuthorByNameQuery;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.litTypes.FindLitTypesByNameQuery;
import com.TermPedia.responses.item.AuthorResponse;
import com.TermPedia.responses.item.LitTypesResponse;
import com.TermPedia.responses.tags.TagResponse;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    public LitTypesResponse getLitTypes(String name, int get, int skip) {
        FindLitTypesByNameQuery query = new FindLitTypesByNameQuery(get, skip, name);
        QueryHandler handler = new QueryHandler();
        handler.handle(query);
        return new LitTypesResponse(query.getResult().getLitTypes());
    }

    public TagResponse getTags(String name, int get, int skip) {
        FindTagByNameQuery query = new FindTagByNameQuery(get, skip, name);
        QueryHandler handler = new QueryHandler();
        handler.handle(query);
        return new TagResponse(query.getResult().getTags());
    }

    public AuthorResponse getAuthors(String name, int get, int skip) {
        FindAuthorByNameQuery query = new FindAuthorByNameQuery(get, skip, name);
        QueryHandler handler = new QueryHandler();
        handler.handle(query);
        return new AuthorResponse(query.getResult().getAuthors());
    }
}
