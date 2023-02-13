package com.TermPedia.factory.query.postgres;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.command.EventData;
import com.TermPedia.factory.query.common.BaseQuerySettingsAssert;
import com.TermPedia.factory.query.common.TagsRequests;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.tags.FindTagByTermIdQuery;
import com.TermPedia.queries.user.UserTermTagRatingQuery;


public class PostgresTagsRequests extends BaseQuerySettingsAssert implements TagsRequests {
    private final StringBuilder builder;
    public PostgresTagsRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String getTagsByNameQuery(FindTagByNameQuery settings) throws ActionsException {
        assertSelectCorrect(settings.getSearchAmount(), settings.getSkipAmount());

        builder.setLength(0);
        builder.append("SELECT name FROM data.tags WHERE lower(name) like lower('");
        builder.append(settings.getName());
        builder.append("%') or plainto_tsquery('");
        builder.append(settings.getName());
        builder.append("') @@ vector ORDER BY name LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }

    @Override
    public String getTagsByTermIdQuery(FindTagByTermIdQuery settings) throws ActionsException {
        assertSelectCorrect(settings.getSearchAmount(), settings.getSearchAmount());


        builder.setLength(0);
        builder.append("SELECT tag, rating, rates_amount FROM data.terms_tags tt WHERE tid = ");
        builder.append(settings.getTermId());

        if (settings.isSearchNew())
            builder.append(" ORDER BY rates_amount, tt.rating DESC ");
        else
            builder.append(" ORDER BY tt.rating DESC, rates_amount DESC ");
        builder.append("LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }

    @Override
    public String userTermTagRating(UserTermTagRatingQuery settings) throws ActionsException {
        builder.setLength(0);
        builder.append("SELECT tag, rating FROM data.term_tag_rates WHERE uid = ");
        builder.append(settings.getUserId());
        builder.append(" and tid = ");
        builder.append(settings.getTermId());
        builder.append(" and tag = '");
        builder.append(settings.getTag());
        builder.append("'");
        return builder.toString();
    }

    @Override
    public String addTagToTermQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.add_tag_term('");
        builder.append(data.json);
        builder.append("');");
        return builder.toString();
    }

    @Override
    public String rateTagTermQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.rate_tag_term('");
        builder.append(data.json);
        builder.append("', ");
        builder.append(data.uid);
        builder.append(");");
        return builder.toString();
    }
}
