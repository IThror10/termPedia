package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.NotFoundException;
import com.TermPedia.dto.tags.RatedTag;
import com.TermPedia.dto.tags.Tag;
import com.TermPedia.dto.users.UserRating;
import com.TermPedia.factory.adapters.ISearchAdapter;
import com.TermPedia.factory.query.common.TagsRequests;
import com.TermPedia.queries.results.tag.RatedTagQueryResult;
import com.TermPedia.queries.results.tag.TagQueryResult;
import com.TermPedia.queries.results.user.UserRatingResult;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.tags.FindTagByTermIdQuery;
import com.TermPedia.queries.user.UserTermTagRatingQuery;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

public class StatementTagSearcher implements TagsSearcher {
    private final ISearchAdapter searcher;
    private final TagsRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("QueryDB"); }

    public StatementTagSearcher(@NotNull ISearchAdapter searcher, @NotNull TagsRequests builder) {
        this.builder = builder;
        this.searcher = searcher;
    }

    @Override
    public TagQueryResult getTagsByName(FindTagByNameQuery settings) throws ActionsException {
        String query = builder.getTagsByNameQuery(settings);
        try {
            Vector<Tag> tags = new Vector<>(settings.getSearchAmount());
            searcher.execute(query);
            while (searcher.next())
                tags.add(new Tag(searcher.getString("name")));
            return new TagQueryResult(tags);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public RatedTagQueryResult getTagsByTerm(FindTagByTermIdQuery settings) throws ActionsException {
        String query = builder.getTagsByTermIdQuery(settings);
        try {
            Vector<RatedTag> tags = new Vector<>(settings.getSearchAmount());
            searcher.execute(query);
            while (searcher.next())
                tags.add(new RatedTag(
                    searcher.getString("tag"),
                    searcher.getDouble("rating"),
                    searcher.getInt("rates_amount")
                ));
            return new RatedTagQueryResult(tags);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public UserRatingResult getUserTermTagRating(UserTermTagRatingQuery settings) throws ActionsException {
        String query = builder.userTermTagRating(settings);
        try {
            searcher.execute(query);
            searcher.next();
            int status = searcher.getInt("status");

            if (status == -1)
                throw new NotFoundException("Tag-TermId connection doesn't exist");
            else {
                return new UserRatingResult(
                        new UserRating(
                                settings.getTermId(),
                                settings.getTag(),
                                searcher.getInt("rating")
                        )
                );
            }
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }
}
