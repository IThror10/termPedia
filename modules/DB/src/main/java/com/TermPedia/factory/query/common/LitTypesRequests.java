package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.instances.IByNameGetSettings;

public interface LitTypesRequests {
    String getLitTypesByNameQuery(IByNameGetSettings settings) throws ActionsException;
}
