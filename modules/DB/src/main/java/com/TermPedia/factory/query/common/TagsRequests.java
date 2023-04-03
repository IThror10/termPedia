package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.command.EventData;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.tags.FindTagByTermIdQuery;
import com.TermPedia.queries.user.UserTermTagRatingQuery;

public interface TagsRequests {
    String getTagsByNameQuery(FindTagByNameQuery settings) throws ActionsException;
    String getTagsByTermIdQuery(FindTagByTermIdQuery settings) throws ActionsException;
    String userTermTagRating(UserTermTagRatingQuery settings) throws ActionsException;
    String addTagToTermQuery(EventData data);
    String rateTagTermQuery(EventData data);
}
