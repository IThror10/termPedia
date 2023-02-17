package com.TermPedia.requests.lit;

import com.TermPedia.queries.lit.FindLitByTagQuery;

public class FindLitByTagRequest extends FindLitByTagQuery {
    public FindLitByTagRequest(int searchAmount, int skipAmount) {
        super(searchAmount, skipAmount);
    }
}
