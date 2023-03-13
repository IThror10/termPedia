package com.TermPedia.queries;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.lit.*;
import com.TermPedia.queries.authors.FindAuthorByNameQuery;
import com.TermPedia.queries.terms.FindTermByIdQuery;
import com.TermPedia.queries.user.UserTermLitRatingQuery;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.tags.FindTagByTermIdQuery;
import com.TermPedia.queries.terms.FindTermByNameQuery;
import com.TermPedia.queries.litTypes.FindLitTypesByNameQuery;
import com.TermPedia.queries.user.GetUserPublicDataQuery;
import com.TermPedia.queries.user.UserTermTagRatingQuery;

public interface QueryVisitor {
    void visitFindTermQuery(FindTermByNameQuery query) throws ActionsException;
    void visitFindTermQuery(FindTermByIdQuery query) throws ActionsException;
    void visitFindTagByNameQuery(FindTagByNameQuery query) throws ActionsException;
    void visitFindTagByTermNameQuery(FindTagByTermIdQuery query) throws ActionsException;
    void visitFindAuthorByNameQuery(FindAuthorByNameQuery query) throws ActionsException;
    void visitFindLitTypesByNameQuery(FindLitTypesByNameQuery query) throws ActionsException;

    void visitSearchBookByNameQuery(FindLitByLikeNameQuery query) throws ActionsException;
    void visitSearchBookByTagQuery(FindLitByTagQuery query) throws ActionsException;
    void visitSearchBookByAuthorQuery(FindLitByAuthorNameQuery query) throws ActionsException;
    void visitSearchBookByTermQuery(FindLitByLikeTermNameQuery query) throws ActionsException;
    void visitSearchConnectedBooksQuery(FindLitByTermIdQuery query) throws ActionsException;
    void visitGetUserPublicDataQuery(GetUserPublicDataQuery query) throws ActionsException;

    void visitUserTermLitRatingQuery(UserTermLitRatingQuery userTermLitRatingQuery) throws ActionsException;
    void visitUserTermTagRatingQuery(UserTermTagRatingQuery userTermTagRatingQuery) throws ActionsException;
}
