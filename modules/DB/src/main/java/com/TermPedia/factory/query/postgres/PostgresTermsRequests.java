package com.TermPedia.factory.query.postgres;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.command.EventData;
import com.TermPedia.factory.query.common.BaseQuerySettingsAssert;
import com.TermPedia.factory.query.common.TermsRequests;
import com.TermPedia.queries.terms.FindTermByNameQuery;

public class PostgresTermsRequests extends BaseQuerySettingsAssert implements TermsRequests {
    private final StringBuilder builder;
    public PostgresTermsRequests() {
        builder = new StringBuilder(256);
    }

    @Override
    public String getTermsByNameQuery(FindTermByNameQuery settings) throws ActionsException {
        assertSelectCorrect(settings.getSearchAmount(), settings.getSkipAmount());

        builder.setLength(0);
        builder.append("SELECT terms.tid, terms.name, terms.description FROM data.terms WHERE lower(name) = lower('");
        builder.append(settings.getTermNameWildcard());
        builder.append("') or plainto_tsquery('");
        builder.append(settings.getTermNameWildcard());
        builder.append("') @@ vector ORDER BY name LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }

    @Override
    public String newTermQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.add_term('");
        builder.append(data.json);
        builder.append("');");
        return builder.toString();
    }

    @Override
    public String addLitToTermQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.add_lit_term('");
        builder.append(data.json);
        builder.append("');");
        return builder.toString();
    }

    @Override
    public String rateLitTermQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.rate_lit_term('");
        builder.append(data.json);
        builder.append("', ");
        builder.append(data.uid);
        builder.append(");");
        return builder.toString();
    }
}
