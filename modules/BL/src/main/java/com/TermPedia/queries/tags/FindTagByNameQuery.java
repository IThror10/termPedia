package com.TermPedia.queries.tags;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.QueryVisitor;
import com.TermPedia.queries.results.tag.TagQueryResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FindTagByNameQuery implements IQuery{
    private TagQueryResult result;
    private final int searchAmount;
    private final int skipAmount;
    private final String name;
    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitFindTagByNameQuery(this);
    }
}
