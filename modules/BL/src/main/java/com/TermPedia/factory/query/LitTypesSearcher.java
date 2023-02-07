package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.instances.IByNameGetSettings;
import com.TermPedia.queries.instances.types.LitTypesQueryResult;

public interface LitTypesSearcher {
    LitTypesQueryResult getLitTypesByName(IByNameGetSettings settings) throws ActionsException;
}
