package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;

public abstract class BaseQuerySettingsAssert {
    public void assertSelectCorrect(Integer search_amount, Integer skip_amount) throws FormatException {
        if (search_amount <= 0)
            throw new FormatException("Search Amount Must Be Positive");
        else if (skip_amount < 0)
            throw new FormatException("Skip Amount Must not be Negative");
    }
}
