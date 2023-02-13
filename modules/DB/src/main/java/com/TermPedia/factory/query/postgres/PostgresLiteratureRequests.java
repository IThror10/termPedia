package com.TermPedia.factory.query.postgres;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.command.EventData;
import com.TermPedia.factory.query.common.BookQuerySettingsAssert;
import com.TermPedia.factory.query.common.BooksRequests;
import com.TermPedia.queries.lit.*;
import com.TermPedia.queries.user.UserTermLitRatingQuery;

import java.util.List;

public class PostgresLiteratureRequests extends BookQuerySettingsAssert implements BooksRequests {
    private final StringBuilder builder;
    public PostgresLiteratureRequests() {
        builder = new StringBuilder(256);
    }

    @Override
    public String authorSearchQuery(FindLitByAuthorNameQuery settings) throws ActionsException {
        assertSelectCorrect(settings.getSearchAmount(), settings.getSkipAmount());

        builder.setLength(0);
        builder.append("SELECT DISTINCT l.name, l.type, l.year, l.authors FROM (SELECT name FROM data.authors WHERE lower(name) like lower('%");
        builder.append(settings.getAuthorWildcard());
        builder.append("%')) as a JOIN data.authors_lit al on a.name = al.author JOIN data.lit l on l.lid = al.lid WHERE l.year >= ");
        builder.append(settings.getYearStart());
        builder.append(" and l.year <= ");
        builder.append(settings.getYearEnd());
        if (settings.getLitType() != null) {
            builder.append(" and l.type = ");
            builder.append(settings.getLitType());
        }
        builder.append(" ORDER BY l.name LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }

    @Override
    public String bookSearchQuery(FindLitByLikeNameQuery settings) throws ActionsException {
        assertSelectCorrect(settings.getSearchAmount(), settings.getSkipAmount());
        assertYearsCorrect(settings.getYearStart(), settings.getYearEnd());

        List<String> tags = settings.getTags();
        boolean with_rating = tags != null && tags.size() > 0;

        builder.setLength(0);
        if (with_rating)
            builder.append("SELECT bs.lid, name, type, year, authors, min(rating) as rating FROM ");
        else
            builder.append("SELECT bs.lid, name, type, year, authors, rating as rating FROM ");
        builder.append("(SELECT LID FROM data.lit WHERE lower(name) like lower('%");
        builder.append(settings.getBookNameWildcard());
        builder.append("%') or vector @@ plainto_tsquery('");
        builder.append(settings.getBookNameWildcard());
        builder.append("')) as l JOIN data.book_search bs on l.LID = bs.LID WHERE year >= ");

        builder.append(settings.getYearStart());
        builder.append(" and year <= ");
        builder.append(settings.getYearEnd());

        if (settings.getLitType() != null) {
            builder.append(" and type = '");
            builder.append(settings.getLitType());
            builder.append("'");
        }

        if (!with_rating)
            builder.append(" and tag is null");
        else {
            builder.append(" and (");
            for (int i = 0; i < tags.size(); i++) {
                if (i > 0)
                    builder.append(" or ");
                builder.append("tag = '");
                builder.append(tags.get(i));
                builder.append("' and rating >= ");
                builder.append(settings.getMinRating());
            }
            builder.append(") GROUP BY (bs.lid, name, type, year, authors) HAVING count(*) = ");
            builder.append(tags.size());

            if (settings.getOrderByRating())
                builder.append(" ORDER BY rating DESC");
        }

        builder.append(" LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }

    @Override
    public String anySearchQuery(FindLitByTagQuery settings) throws ActionsException {
        assertSelectCorrect(settings.getSearchAmount(), settings.getSkipAmount());
        assertYearsCorrect(settings.getYearStart(), settings.getYearEnd());

        List<String> tags = settings.getTags();
        boolean with_rating = tags != null && tags.size() > 0;

        builder.setLength(0);
        if (with_rating)
            builder.append("SELECT lid, name, type, year, authors, min(rating) as rating FROM data.book_search WHERE year >= ");
        else
            builder.append("SELECT lid, name, type, year, authors, rating as rating FROM data.book_search WHERE year >= ");

        builder.append(settings.getYearStart());
        builder.append(" and year <= ");
        builder.append(settings.getYearEnd());

        if (settings.getLitType() != null) {
            builder.append(" and type = '");
            builder.append(settings.getLitType());
            builder.append("'");
        }


        if (!with_rating)
            builder.append(" and tag is null");
        else {
            builder.append(" and (");
            for (int i = 0; i < tags.size(); i++) {
                if (i > 0)
                    builder.append(" or ");
                builder.append("tag = '");
                builder.append(tags.get(i));
                builder.append("' and rating >= ");
                builder.append(settings.getMinRating());
            }
            builder.append(") GROUP BY (lid, name, type, year, authors) HAVING count(*) = ");
            builder.append(tags.size());

            if (settings.getOrderByRating())
                builder.append(" ORDER BY rating DESC");
        }

        builder.append(" LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }

    @Override
    public String termSearchQuery(FindLitByLikeTermNameQuery settings) throws ActionsException {
        assertSelectCorrect(settings.getSearchAmount(), settings.getSkipAmount());
        assertYearsCorrect(settings.getYearStart(), settings.getYearEnd());

        builder.setLength(0);

        builder.append("with terms(tid) as (SELECT tid FROM data.terms WHERE lower(name) = lower('");
        builder.append(settings.getTermWildcard());
        builder.append("') or vector @@ plainto_tsquery('");
        builder.append(settings.getTermWildcard());
        builder.append("')), term_lit (LID, rating, rates_amount) as " +
                "(SELECT LID, rating, rates_amount FROM (SELECT tl.LID, rating, rates_amount, " +
                "row_number() over (partition by LID order by rating DESC) as rn FROM terms t " +
                "JOIN data.terms_lit tl on tl.tid = t.tid and tl.rating >= ");
        builder.append(settings.getMinRating());
        builder.append(") as temp WHERE rn = 1) SELECT l.lid, l.name, l.type, l.year, l.authors, tl.rating, " +
                "tl.rates_amount FROM term_lit tl JOIN data.lit l on l.LID = tl.LID WHERE year >= ");

        builder.append(settings.getYearStart());
        builder.append(" and year <= ");
        builder.append(settings.getYearEnd());

        if (settings.getLitType() != null) {
            builder.append(" and type = '");
            builder.append(settings.getLitType());
            builder.append("'");
        }

        if (settings.getOrderByRating() && settings.getRecentlyAdded())
            builder.append(" ORDER BY tl.rates_amount, tl.rating DESC");
        else if (settings.getOrderByRating())
            builder.append(" ORDER BY tl.rating DESC");
        else if (settings.getRecentlyAdded())
            builder.append(" ORDER BY tl.rates_amount");

        builder.append(" LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }

    @Override
    public String termIdSearchQuery(FindLitByTermIdQuery settings) throws ActionsException {
        assertSelectCorrect(settings.getSearchAmount(), settings.getSkipAmount());
        assertYearsCorrect(settings.getYearStart(), settings.getYearEnd());

        builder.setLength(0);
        builder.append("SELECT l.lid, l.name, l.type, l.year, l.authors, tl.rating, tl.rates_amount ");
        builder.append("FROM data.terms_lit tl JOIN data.lit l on tl.lid = l.lid and tl.tid = ");
        builder.append(settings.getTermId());
        builder.append(" and tl.rating >= ");
        builder.append(settings.getMinRating());
        builder.append(" and year >= ");
        builder.append(settings.getYearStart());
        builder.append(" and year <= ");
        builder.append(settings.getYearEnd());

        if (settings.getOrderByRating() && settings.getRecentlyAdded())
            builder.append(" ORDER BY tl.rates_amount, tl.rating DESC");
        else if (settings.getOrderByRating())
            builder.append(" ORDER BY tl.rating DESC");
        else if (settings.getRecentlyAdded())
            builder.append(" ORDER BY tl.rates_amount");

        builder.append(" LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }

    @Override
    public String userTermLitRatingQuery(UserTermLitRatingQuery settings) throws ActionsException {
        builder.setLength(0);
        builder.append("SELECT lid, rating FROM data.term_lit_rates WHERE uid = ");
        builder.append(settings.getUserID());
        builder.append(" and lid = ");
        builder.append(settings.getLitID());
        builder.append(" and tid = ");
        builder.append(settings.getTermID());
        return builder.toString();
    }

    public String addLiterature(EventData data) {
        builder.setLength(0);
        builder.append("call data.add_lit('");
        builder.append(data.json);
        builder.append("');");
        return builder.toString();
    }
}