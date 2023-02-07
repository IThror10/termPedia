package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.books.BaseSearchBookByAuthorNameQuery;
import com.TermPedia.queries.books.BaseSearchBookByBookNameQuery;
import com.TermPedia.queries.books.BaseSearchBookByTagQuery;
import com.TermPedia.queries.books.BaseSearchBookByTermQuery;

public interface BooksRequests {
    String authorSearchQuery(BaseSearchBookByAuthorNameQuery query) throws ActionsException;
    String bookSearchQuery(BaseSearchBookByBookNameQuery query) throws ActionsException;
    String anySearchQuery(BaseSearchBookByTagQuery query) throws ActionsException;
    String termSearchQuery(BaseSearchBookByTermQuery query) throws ActionsException;
    String connectedTermsSearchQuery(BaseSearchBookByTermQuery query) throws ActionsException;
}
