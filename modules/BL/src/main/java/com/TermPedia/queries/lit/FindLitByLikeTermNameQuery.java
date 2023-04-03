package com.TermPedia.queries.lit;

import  com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.results.lit.RatedLiteratureQueryResult;
import com.TermPedia.queries.QueryVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class FindLitByLikeTermNameQuery implements IQuery {
    private RatedLiteratureQueryResult result;

    private Boolean orderByRating = true;
    private Boolean recentlyAdded = false;
    private Double minRating = 0.0;
    private String litType = null;
    private Integer yearStart = -3000;
    private Integer yearEnd = 3000;
    private List<String> tags = null;

    private final int searchAmount;
    private final int skipAmount;
    private final String termWildcard;
    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitSearchBookByTermQuery(this);
    }
}
