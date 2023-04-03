package com.TermPedia.factory.query.postgres;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.query.common.AuthorsRequests;
import com.TermPedia.factory.query.common.BaseQuerySettingsAssert;
import com.TermPedia.queries.authors.FindAuthorByNameQuery;

public class PostgresAuthorsRequests extends BaseQuerySettingsAssert implements AuthorsRequests {
    private final StringBuilder builder;
    public PostgresAuthorsRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String getAuthorsByNameQuery(FindAuthorByNameQuery query) throws ActionsException {
        assertSelectCorrect(query.getSearchAmount(), query.getSkipAmount());

        builder.setLength(0);
        builder.append("SELECT name FROM data.authors WHERE lower(name) like lower('%");
        builder.append(query.getName());
        builder.append("%') ORDER BY name LIMIT ");
        builder.append(query.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(query.getSkipAmount());
        return builder.toString();
    }
}
