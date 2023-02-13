package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.FormatException;

public abstract class BookQuerySettingsAssert extends BaseQuerySettingsAssert {
    public void assertYearsCorrect(Integer year_start, Integer year_end) throws FormatException {
        if (year_start != null && year_end != null && year_start > year_end)
            throw new FormatException("Wrong Year Limits");
    }
}
