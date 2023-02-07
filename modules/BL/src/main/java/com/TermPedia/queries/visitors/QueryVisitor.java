package com.TermPedia.queries.visitors;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.books.*;
import com.TermPedia.queries.instances.authors.FindAuthorByNameQuery;
import com.TermPedia.queries.instances.tags.FindTagByNameQuery;
import com.TermPedia.queries.instances.tags.FindTagByTermNameQuery;
import com.TermPedia.queries.instances.terms.FindTermByNameQuery;
import com.TermPedia.queries.instances.types.FindLitTypesByNameQuery;
import com.TermPedia.queries.instances.users.GetUserPublicDataQuery;

public interface QueryVisitor {
    void visitFindTermQuery(FindTermByNameQuery query) throws ActionsException;
    void visitFindTagByNameQuery(FindTagByNameQuery query) throws ActionsException;
    void visitFindTagByTermNameQuery(FindTagByTermNameQuery query) throws ActionsException;
    void visitFindAuthorByNameQuery(FindAuthorByNameQuery query) throws ActionsException;
    void visitFindLitTypesByNameQuery(FindLitTypesByNameQuery query) throws ActionsException;

    void visitSearchBookByNameQuery(SearchBookByLikeNameQuery query) throws ActionsException;
    void visitSearchBookByTagQuery(SearchBookByTagQuery query) throws ActionsException;
    void visitSearchBookByAuthorQuery(SearchBookByAuthorQuery query) throws ActionsException;
    void visitSearchBookByTermQuery(SearchBookByLikeTermQuery query) throws ActionsException;
    void visitSearchConnectedBooksQuery(SearchBookByTermQuery query) throws ActionsException;
    void visitGetUserPublicDataQuery(GetUserPublicDataQuery query) throws ActionsException;
}
