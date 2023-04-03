package com.TermPedia.queries;

import com.TermPedia.dto.exceptions.ActionsException;

public interface IQuery {
    void acceptVisitor(QueryVisitor visitor) throws ActionsException;
}
