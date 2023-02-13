package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.lit.*;
import com.TermPedia.queries.user.UserTermLitRatingQuery;

public interface BooksRequests {
    String authorSearchQuery(FindLitByAuthorNameQuery settings) throws ActionsException;
    String bookSearchQuery(FindLitByLikeNameQuery settings) throws ActionsException;
    String anySearchQuery(FindLitByTagQuery settings) throws ActionsException;
    String termSearchQuery(FindLitByLikeTermNameQuery settings) throws ActionsException;
    String termIdSearchQuery(FindLitByTermIdQuery settings) throws ActionsException;
    String userTermLitRatingQuery(UserTermLitRatingQuery settings) throws ActionsException;
}
