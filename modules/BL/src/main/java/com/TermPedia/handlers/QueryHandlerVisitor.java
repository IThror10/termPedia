package com.TermPedia.handlers;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.term.Term;
import com.TermPedia.factory.command.CommandFactory;
import com.TermPedia.factory.command.UserCommandHandler;
import com.TermPedia.queries.QueryVisitor;
import com.TermPedia.queries.lit.*;
import com.TermPedia.factory.query.*;
import com.TermPedia.queries.results.author.AuthorQueryResult;
import com.TermPedia.queries.authors.FindAuthorByNameQuery;
import com.TermPedia.queries.results.user.UserRatingResult;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.tags.FindTagByTermIdQuery;
import com.TermPedia.queries.results.tag.RatedTagQueryResult;
import com.TermPedia.queries.results.tag.TagQueryResult;
import com.TermPedia.queries.terms.FindTermByIdQuery;
import com.TermPedia.queries.terms.FindTermByNameQuery;
import com.TermPedia.queries.results.term.TermQueryResult;
import com.TermPedia.queries.litTypes.FindLitTypesByNameQuery;
import com.TermPedia.queries.results.litType.LitTypesQueryResult;
import com.TermPedia.queries.user.GetUserPublicDataQuery;
import com.TermPedia.queries.results.user.UserPublicDataQueryResult;
import com.TermPedia.queries.results.lit.LiteratureQueryResult;
import com.TermPedia.queries.results.lit.RatedLiteratureQueryResult;
import com.TermPedia.queries.results.lit.TagLiteratureQueryResult;
import com.TermPedia.queries.user.UserTermLitRatingQuery;
import com.TermPedia.queries.user.UserTermTagRatingQuery;

public class QueryHandlerVisitor implements QueryVisitor {
    @Override
    public void visitFindTermQuery(FindTermByNameQuery query) throws ActionsException {
        TermsSearcher searcher = QueryFactory.instance().createTermSearcher();
        TermQueryResult result = searcher.getTermsByName(query);
        query.setResult(result);
    }

    @Override
    public void visitFindTermQuery(FindTermByIdQuery query) throws ActionsException {
        TermsSearcher searcher = QueryFactory.instance().createTermSearcher();
        Term result = searcher.getTermById(query);
        query.setResult(result);
    }

    @Override
    public void visitFindTagByNameQuery(FindTagByNameQuery query) throws ActionsException {
        TagsSearcher searcher = QueryFactory.instance().createTagSearcher();
        TagQueryResult result = searcher.getTagsByName(query);
        query.setResult(result);
    }

    @Override
    public void visitFindTagByTermNameQuery(FindTagByTermIdQuery query) throws ActionsException {
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
    public void visitSearchBookByNameQuery(FindLitByLikeNameQuery query) throws ActionsException {
        LiteratureSearcher searcher = QueryFactory.instance().createBookSearcher();
        TagLiteratureQueryResult result = searcher.searchByBookName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByTagQuery(FindLitByTagQuery query) throws ActionsException {
        LiteratureSearcher searcher = QueryFactory.instance().createBookSearcher();
        TagLiteratureQueryResult result = searcher.searchByTagName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByAuthorQuery(FindLitByAuthorNameQuery query) throws ActionsException {
        LiteratureSearcher searcher = QueryFactory.instance().createBookSearcher();
        LiteratureQueryResult result = searcher.searchByAuthorName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchBookByTermQuery(FindLitByLikeTermNameQuery query) throws ActionsException {
        LiteratureSearcher searcher = QueryFactory.instance().createBookSearcher();
        RatedLiteratureQueryResult result = searcher.searchByTermName(query);
        query.setResult(result);
    }

    @Override
    public void visitSearchConnectedBooksQuery(FindLitByTermIdQuery query) throws ActionsException {
        LiteratureSearcher searcher = QueryFactory.instance().createBookSearcher();
        RatedLiteratureQueryResult result = searcher.searchByTermId(query);
        query.setResult(result);
    }

    @Override
    public void visitGetUserPublicDataQuery(GetUserPublicDataQuery query) throws ActionsException {
        UserCommandHandler handler = CommandFactory.instance().createReqAuthHandler();
        UserPublicDataQueryResult result = handler.getInfo(query);
        query.setResult(result);
    }

    @Override
    public void visitUserTermLitRatingQuery(UserTermLitRatingQuery query) throws ActionsException {
        LiteratureSearcher searcher = QueryFactory.instance().createBookSearcher();
        UserRatingResult result = searcher.getUserTermLitRating(query);
        query.setResult(result);
    }

    @Override
    public void visitUserTermTagRatingQuery(UserTermTagRatingQuery query) throws ActionsException {
        TagsSearcher searcher = QueryFactory.instance().createTagSearcher();
        UserRatingResult result = searcher.getUserTermTagRating(query);
        query.setResult(result);
    }
}
