package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.adapters.ISearchAdapter;
import com.TermPedia.factory.query.common.AuthorsRequests;
import com.TermPedia.queries.authors.FindAuthorByNameQuery;
import com.TermPedia.queries.results.author.AuthorQueryResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StatementAuthorsSearcher implements AuthorsSearcher {
    private final ISearchAdapter searcher;
    private final AuthorsRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("QueryDB"); }

    public StatementAuthorsSearcher(@NotNull ISearchAdapter searcher, @NotNull AuthorsRequests builder) {
        this.builder = builder;
        this.searcher = searcher;
    }
    @Override
    public AuthorQueryResult getAuthorByName(FindAuthorByNameQuery settings) throws ActionsException {
        String query = builder.getAuthorsByNameQuery(settings);
        try {
            List<String> authors = new ArrayList<>(settings.getSearchAmount());
            searcher.execute(query);
            while (searcher.next())
                authors.add(searcher.getString("name"));
            return new AuthorQueryResult(authors);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }
}
