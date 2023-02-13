package com.TermPedia.queries.tags;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.QueryVisitor;
import com.TermPedia.queries.results.tag.RatedTagQueryResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FindTagByTermIdQuery implements IQuery {
    private RatedTagQueryResult result;

    private final int searchAmount;
    private final int skipAmount;
    private final Integer termId;
    private final boolean searchNew;

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitFindTagByTermNameQuery(this);
    }

}
