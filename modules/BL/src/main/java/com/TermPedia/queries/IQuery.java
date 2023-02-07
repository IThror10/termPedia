package com.TermPedia.queries;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.visitors.QueryVisitor;

public interface IQuery {
    void acceptVisitor(QueryVisitor visitor) throws ActionsException;
    QueryResult getResult();
}
