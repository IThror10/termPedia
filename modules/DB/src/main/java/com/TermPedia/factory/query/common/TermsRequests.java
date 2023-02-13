package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.term.Term;
import com.TermPedia.factory.command.EventData;
import com.TermPedia.queries.terms.FindTermByNameQuery;

public interface TermsRequests {
    String getTermsByNameQuery(FindTermByNameQuery query) throws ActionsException;
    String newTermQuery(EventData data) throws ActionsException;
    String addLitToTermQuery(EventData data);
    String rateLitTermQuery(EventData data);
}
