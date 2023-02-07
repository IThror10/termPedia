package com.TermPedia.queries.instances.users;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.visitors.QueryVisitor;

public class GetUserPublicDataQuery implements IQuery {
    private UserPublicDataQueryResult result;
    public final String userLogin;

    public GetUserPublicDataQuery(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitGetUserPublicDataQuery(this);
    }

    @Override
    public UserPublicDataQueryResult getResult() {
        return result;
    }

    public void setResult(UserPublicDataQueryResult result) {
        this.result = result;
    }
}
