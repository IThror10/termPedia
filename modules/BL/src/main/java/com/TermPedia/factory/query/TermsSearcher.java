package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.results.term.TermQueryResult;
import com.TermPedia.queries.terms.FindTermByNameQuery;

public interface TermsSearcher {
    TermQueryResult getTermsByName(FindTermByNameQuery query) throws ActionsException;
}
