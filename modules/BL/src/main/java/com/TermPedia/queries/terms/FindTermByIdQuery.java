package com.TermPedia.queries.terms;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.term.Term;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.QueryVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FindTermByIdQuery implements IQuery {
    private Term result;
    private final Integer tid;

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitFindTermQuery(this);
    }
}
