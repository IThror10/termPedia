package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.Term;
import com.TermPedia.queries.instances.IByNameGetSettings;
import com.TermPedia.queries.instances.terms.TermQueryResult;
public interface TermsSearcher {
    TermQueryResult getTermsByName(IByNameGetSettings settings) throws ActionsException;
    boolean termExists(Term term) throws ActionsException;
}
