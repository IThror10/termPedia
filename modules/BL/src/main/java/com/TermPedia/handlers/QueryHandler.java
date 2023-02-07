package com.TermPedia.handlers;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.visitors.QueryHandlerVisitor;

public class QueryHandler {
    public void handle(IQuery query) throws ActionsException {
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        query.acceptVisitor(visitor);
    }
}
