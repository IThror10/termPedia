package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.authors.FindAuthorByNameQuery;

public interface AuthorsRequests {
    String getAuthorsByNameQuery(FindAuthorByNameQuery query) throws ActionsException;
}
