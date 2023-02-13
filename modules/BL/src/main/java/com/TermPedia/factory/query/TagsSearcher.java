package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.results.tag.RatedTagQueryResult;
import com.TermPedia.queries.results.tag.TagQueryResult;
import com.TermPedia.queries.results.user.UserRatingResult;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.tags.FindTagByTermIdQuery;
import com.TermPedia.queries.user.UserTermTagRatingQuery;

public interface TagsSearcher {
    TagQueryResult getTagsByName(FindTagByNameQuery query) throws ActionsException;
    RatedTagQueryResult getTagsByTerm(FindTagByTermIdQuery query) throws ActionsException;
    UserRatingResult getUserTermTagRating(UserTermTagRatingQuery query) throws ActionsException;
}
