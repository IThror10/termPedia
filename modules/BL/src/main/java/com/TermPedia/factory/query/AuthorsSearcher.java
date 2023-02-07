package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.instances.authors.AuthorQueryResult;
import com.TermPedia.queries.instances.IByNameGetSettings;

public interface AuthorsSearcher {
    AuthorQueryResult getAuthorByName(IByNameGetSettings settings) throws ActionsException;
}
