package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.litTypes.FindLitTypesByNameQuery;
import com.TermPedia.queries.results.litType.LitTypesQueryResult;

public interface LitTypesSearcher {
    LitTypesQueryResult getLitTypesByName(FindLitTypesByNameQuery query) throws ActionsException;
}
