package com.TermPedia.factory.query.common;

import com.TermPedia.factory.command.EventData;

public interface UpdateRequests {
    String newUserQuery(EventData data);
    String newTermQuery(EventData data);
    String newLitTermPareQuery(EventData data);
    String newTagTermPareQuery(EventData data);
    String newRateTermLitQuery(EventData data);
    String newRateTermTagQuery(EventData data);
    String newLitQuery(EventData data);
}
