package com.TermPedia.queries.instances.users;

import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.queries.QueryResult;

public class UserPublicDataQueryResult extends QueryResult {
    private final UserPublicData user;

    public UserPublicDataQueryResult(UserPublicData user) {
        this.user = user;
    }

    public UserPublicData getUserData() { return user; }
}
