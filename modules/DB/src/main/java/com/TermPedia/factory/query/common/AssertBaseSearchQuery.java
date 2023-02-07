package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.books.BaseSearchQuery;

public abstract class AssertBaseSearchQuery {
    public void assertCorrect(BaseSearchQuery settings) throws ActionsException {
        if (settings.getYearStart() > settings.getYearEnd())
            throw new ActionsException("Wrong Year Limits");
        else if (settings.getSearchAmount() <= 0)
            throw new ActionsException("Select Amount Must Be Positive");
        else if (settings.getSkipAmount() < 0)
            throw new ActionsException("Skip Amount Must Be Positive");
    }
}
