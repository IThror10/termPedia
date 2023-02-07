package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.instances.IByNameGetSettings;

public interface AuthorsRequests {
    String getAuthorsByNameQuery(IByNameGetSettings settings) throws ActionsException;
}
