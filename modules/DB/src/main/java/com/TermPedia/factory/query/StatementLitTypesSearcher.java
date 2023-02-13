package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.adapters.ISearchAdapter;
import com.TermPedia.factory.query.common.LitTypesRequests;
import com.TermPedia.queries.litTypes.FindLitTypesByNameQuery;
import com.TermPedia.queries.results.litType.LitTypesQueryResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StatementLitTypesSearcher implements LitTypesSearcher {
    private final ISearchAdapter searcher;
    private final LitTypesRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("QueryDB"); }

    public StatementLitTypesSearcher(@NotNull ISearchAdapter searcher, @NotNull LitTypesRequests builder) {
        this.builder = builder;
        this.searcher = searcher;
    }
    @Override
    public LitTypesQueryResult getLitTypesByName(FindLitTypesByNameQuery settings) throws ActionsException {
        String query = builder.getLitTypesByNameQuery(settings);
        try {
            List<String> litTypes = new ArrayList<>(settings.getSearchAmount());
            searcher.execute(query);
            while (searcher.next())
                litTypes.add(searcher.getString("name"));
            return new LitTypesQueryResult(litTypes);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }
}
