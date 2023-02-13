package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.term.Term;
import com.TermPedia.factory.adapters.ISearchAdapter;
import com.TermPedia.factory.query.common.TermsRequests;
import com.TermPedia.queries.results.term.TermQueryResult;
import com.TermPedia.queries.terms.FindTermByNameQuery;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;
import java.util.logging.Logger;

public class StatementTermsSearcher implements TermsSearcher {
    private final ISearchAdapter searcher;
    private final TermsRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("QueryDB"); }

    public StatementTermsSearcher(@NotNull ISearchAdapter searcher, @NotNull TermsRequests builder) {
        this.builder = builder;
        this.searcher = searcher;
    }

    @Override
    public TermQueryResult getTermsByName(FindTermByNameQuery settings) throws ActionsException {
        String query = builder.getTermsByNameQuery(settings);
        try {
            Vector<Term> terms = new Vector<>(settings.getSearchAmount());
            searcher.execute(query);
            while (searcher.next())
                terms.add(new Term(
                        searcher.getInt("tid"),
                        searcher.getString("name"),
                        searcher.getString("description")
                ));
            return new TermQueryResult(terms);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }
}
