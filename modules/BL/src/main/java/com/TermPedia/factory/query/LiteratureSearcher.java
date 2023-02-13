package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.lit.*;
import com.TermPedia.queries.results.lit.LiteratureQueryResult;
import com.TermPedia.queries.results.lit.RatedLiteratureQueryResult;
import com.TermPedia.queries.results.lit.TagLiteratureQueryResult;
import com.TermPedia.queries.results.user.UserRatingResult;
import com.TermPedia.queries.user.UserTermLitRatingQuery;

public interface LiteratureSearcher {
    TagLiteratureQueryResult searchByBookName(FindLitByLikeNameQuery query) throws ActionsException;
    TagLiteratureQueryResult searchByTagName(FindLitByTagQuery query) throws ActionsException;
    LiteratureQueryResult searchByAuthorName(FindLitByAuthorNameQuery query) throws ActionsException;
    RatedLiteratureQueryResult searchByTermName(FindLitByLikeTermNameQuery query) throws ActionsException;
    RatedLiteratureQueryResult searchByTermId(FindLitByTermIdQuery query) throws ActionsException;

    UserRatingResult getUserTermLitRating(UserTermLitRatingQuery query) throws ActionsException;
}
