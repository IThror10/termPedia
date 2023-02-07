package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.books.*;

public interface BookSearcher {
    TagBookQueryResult searchByBookName(BaseSearchBookByBookNameQuery query) throws ActionsException;
    TagBookQueryResult searchByTagName(BaseSearchBookByTagQuery query) throws ActionsException;
    BookQueryResult searchByAuthorName(BaseSearchBookByAuthorNameQuery query) throws ActionsException;
    RatedBookQueryResult searchByTermName(BaseSearchBookByTermQuery query) throws ActionsException;
    RatedBookQueryResult searchConnectedBooks(BaseSearchBookByTermQuery query) throws ActionsException;
}
