package com.TermPedia.factory.query.postgres;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.query.common.BaseQuerySettingsAssert;
import com.TermPedia.factory.query.common.LitTypesRequests;
import com.TermPedia.queries.litTypes.FindLitTypesByNameQuery;

public class PostgresLitTypesRequests extends BaseQuerySettingsAssert implements LitTypesRequests {
    private final StringBuilder builder;
    public PostgresLitTypesRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String getLitTypesByNameQuery(FindLitTypesByNameQuery settings) throws ActionsException {
        assertSelectCorrect(settings.getSearchAmount(), settings.getSkipAmount());

        builder.setLength(0);
        builder.append("SELECT name FROM data.lit_types WHERE lower(name) = lower('");
        builder.append(settings.getName());
        builder.append("') or plainto_tsquery('");
        builder.append(settings.getName());
        builder.append("') @@ vector ORDER BY name LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }
}