package com.TermPedia.factory.query.postgres;

import com.TermPedia.factory.command.EventData;
import com.TermPedia.factory.query.common.UpdateRequests;

public class PostgresUpdateRequests implements UpdateRequests {
    @Override
    public String newUserQuery(EventData data) {
        return new PostgresUsersRequests().addUserQuery(data);
    }

    @Override
    public String newTermQuery(EventData data) {
        return new PostgresTermsRequests().newTermQuery(data);
    }

    @Override
    public String newLitTermPareQuery(EventData data) {
        return new PostgresTermsRequests().addLitToTermQuery(data);
    }

    @Override
    public String newTagTermPareQuery(EventData data) {
        return new PostgresTagsRequests().addTagToTermQuery(data);
    }

    @Override
    public String newRateTermLitQuery(EventData data) {
        return new PostgresTermsRequests().rateLitTermQuery(data);
    }

    @Override
    public String newRateTermTagQuery(EventData data) {
        return new PostgresTagsRequests().rateTagTermQuery(data);
    }

    @Override
    public String newLitQuery(EventData data) { return new PostgresLiteratureRequests().addLiterature(data); }
}
