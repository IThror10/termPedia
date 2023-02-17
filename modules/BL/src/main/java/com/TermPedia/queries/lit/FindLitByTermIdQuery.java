package com.TermPedia.queries.lit;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.results.lit.RatedLiteratureQueryResult;
import com.TermPedia.queries.QueryVisitor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class FindLitByTermIdQuery implements IQuery {
    private RatedLiteratureQueryResult result;

    private Boolean orderByRating = true;
    private Boolean recentlyAdded = false;

    private final int searchAmount;
    private final int skipAmount;
    private final Integer termId;

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitSearchConnectedBooksQuery(this);
    }
}

