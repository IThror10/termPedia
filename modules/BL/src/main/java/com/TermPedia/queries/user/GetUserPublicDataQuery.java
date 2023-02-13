package com.TermPedia.queries.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.QueryVisitor;
import com.TermPedia.queries.results.user.UserPublicDataQueryResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class GetUserPublicDataQuery implements IQuery {
    private UserPublicDataQueryResult result;
    public final String userLogin;

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitGetUserPublicDataQuery(this);
    }
}
