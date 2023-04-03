package com.TermPedia.queries.terms;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.QueryVisitor;
import com.TermPedia.queries.results.term.TermQueryResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class FindTermByNameQuery implements IQuery {
    private TermQueryResult result;
    private final int searchAmount;
    private final int skipAmount;
    private final String termNameWildcard;

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitFindTermQuery(this);
    }
}
