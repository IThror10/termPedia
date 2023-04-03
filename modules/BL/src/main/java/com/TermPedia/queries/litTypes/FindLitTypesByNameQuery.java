package com.TermPedia.queries.litTypes;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.QueryVisitor;
import com.TermPedia.queries.results.litType.LitTypesQueryResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FindLitTypesByNameQuery implements IQuery {
    private LitTypesQueryResult result;
    private final int searchAmount;
    private final int skipAmount;
    private final String name;
    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitFindLitTypesByNameQuery(this);
    }
}
