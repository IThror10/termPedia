package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.litTypes.FindLitTypesByNameQuery;

public interface LitTypesRequests {
    String getLitTypesByNameQuery(FindLitTypesByNameQuery query) throws ActionsException;
}
