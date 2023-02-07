package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.Term;
import com.TermPedia.factory.command.EventData;
import com.TermPedia.queries.instances.IByNameGetSettings;

public interface TermsRequests {
    String getTermsByNameQuery(IByNameGetSettings settings) throws ActionsException;
    String termsExists(Term term);
    String newTermQuery(EventData data) throws ActionsException;
    String addLitToTermQuery(EventData data);
    String rateLitTermQuery(EventData data);
}
