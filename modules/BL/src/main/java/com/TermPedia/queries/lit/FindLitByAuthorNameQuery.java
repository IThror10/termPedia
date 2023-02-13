package com.TermPedia.queries.lit;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.results.lit.LiteratureQueryResult;
import com.TermPedia.queries.QueryVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class FindLitByAuthorNameQuery implements IQuery {
    private LiteratureQueryResult result;

    private String litType = null;
    private int yearStart = 3000;
    private int yearEnd = -3000;

    private final int searchAmount;
    private final int skipAmount;
    private final String authorWildcard;

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitSearchBookByAuthorQuery(this);
    }
}
