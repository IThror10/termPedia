package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.authors.FindAuthorByNameQuery;
import com.TermPedia.queries.results.author.AuthorQueryResult;

public interface AuthorsSearcher {
    AuthorQueryResult getAuthorByName(FindAuthorByNameQuery query) throws ActionsException;
}
