package com.TermPedia.queries.visitors;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.command.CommandFactory;
import com.TermPedia.factory.command.ReqAuthHandler;
import com.TermPedia.queries.books.*;
import com.TermPedia.factory.query.*;
import com.TermPedia.queries.instances.authors.AuthorQueryResult;
import com.TermPedia.queries.instances.authors.FindAuthorByNameQuery;
import com.TermPedia.queries.instances.tags.FindTagByNameQuery;
import com.TermPedia.queries.instances.tags.FindTagByTermNameQuery;
import com.TermPedia.queries.instances.tags.RatedTagQueryResult;
import com.TermPedia.queries.instances.tags.TagQueryResult;
import com.TermPedia.queries.instances.terms.FindTermByNameQuery;
import com.TermPedia.queries.instances.terms.TermQueryResult;
import com.TermPedia.queries.instances.types.FindLitTypesByNameQuery;
import com.TermPedia.queries.instances.types.LitTypesQueryResult;
import com.TermPedia.queries.instances.users.GetUserPublicDataQuery;
import com.TermPedia.queries.instances.users.UserPublicDataQueryResult;

public class QueryHandlerVisitor implements QueryVisitor {
    @Override
    public void visitFindTermQuery(FindTermByNameQuery query) throws ActionsException {
        TermsSearcher searcher = QueryFactory.instance().createTermSearcher();
        TermQueryResult result = searcher.getTermsByName(query);
        query.setResult(result);
    }

    @Override
    public void visitFindTagByNameQuery(FindTagByNameQuery query) throws ActionsException {
        TagsSearcher searcher = QueryFactory.instance().createTagSearcher();
        TagQueryResult result = searcher.getTagsByName(query);
        query.setResult(result);
    }

    @Override
    public void visitFindTagByTermNameQuery(FindTagByTermNameQuery query) throws ActionsException {
        TagsSearcher searcher = QueryFactory.instance().createTagSearcher();
        RatedTagQueryResult result = searcher.getTagsByTerm(query);
        query.setResult(result);
    }

    @Override
    public void visitFindAuthorByNameQuery(FindAuthorByNameQuery query) throws ActionsException {
        AuthorsSearcher searcher = QueryFactory.instance().createAuthorSearcher();
        AuthorQueryResult result = searcher.getAuthorByName(query);
        query.setResult(result);
    }

    @Override
    public void visitFindLitTypesByNameQuery(FindLitTypesByNameQuery query) throws ActionsException {
        LitTypesSearcher searcher = QueryFactory.instance().createLitTypesSearcher();
        LitTypesQueryResult result = searcher.getLitTypesByName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByNameQuery(SearchBookByLikeNameQuery query) throws ActionsException {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();
        TagBookQueryResult result = searcher.searchByBookName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByTagQuery(SearchBookByTagQuery query) throws ActionsException {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();
        TagBookQueryResult result = searcher.searchByTagName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByAuthorQuery(SearchBookByAuthorQuery query) throws ActionsException {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();
        BookQueryResult result = searcher.searchByAuthorName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByTermQuery(SearchBookByLikeTermQuery query) throws ActionsException {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();
        RatedBookQueryResult result = searcher.searchByTermName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchConnectedBooksQuery(SearchBookByTermQuery query) throws ActionsException {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();
        RatedBookQueryResult result = searcher.searchConnectedBooks(query);
        query.setResult(result);
    }

    @Override
    public void visitGetUserPublicDataQuery(GetUserPublicDataQuery query) throws ActionsException {
        ReqAuthHandler handler = CommandFactory.instance().createReqAuthHandler();
        UserPublicDataQueryResult result = handler.getInfo(query);
        query.setResult(result);
    }
}
