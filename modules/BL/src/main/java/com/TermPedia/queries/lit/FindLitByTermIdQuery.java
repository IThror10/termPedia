package com.TermPedia.queries.lit;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.results.lit.RatedLiteratureQueryResult;
import com.TermPedia.queries.QueryVisitor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class FindLitByTermIdQuery implements IQuery {
    private RatedLiteratureQueryResult result;

    private Boolean orderByRating = true;
    private Boolean recentlyAdded = false;
    private Double minRating = 0.0;
    private Integer yearStart = -3000;
    private Integer yearEnd = 3000;
    private List<String> tags = null;

    private final int searchAmount;
    private final int skipAmount;
    private final Integer termId;

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitSearchConnectedBooksQuery(this);
    }
}

