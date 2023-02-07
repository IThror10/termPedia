package com.TermPedia.queries.books;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.visitors.QueryVisitor;
import org.jetbrains.annotations.NotNull;

public class SearchBookByLikeTermQuery extends BaseSearchBookByTermQuery implements IQuery {
    private RatedBookQueryResult result;
    public SearchBookByLikeTermQuery(@NotNull String termName, boolean orderByRating, boolean recentlyAdded,
                                     double minRating, Integer uid, int searchAmount, int skipAmount) {
        super(termName, orderByRating, recentlyAdded, minRating, uid, searchAmount, skipAmount);
    }

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitSearchBookByTermQuery(this);
    }

    @Override
    public RatedBookQueryResult getResult() {
        return result;
    }

    public void setResult(RatedBookQueryResult result) { this.result = result; }
}
