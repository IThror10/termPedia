package com.TermPedia.factory.query.postgres;

import com.TermPedia.factory.command.EventData;
import com.TermPedia.factory.query.common.UsersRequests;

public class PostgresUsersRequests implements UsersRequests {
    private final StringBuilder builder;
    public PostgresUsersRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String addUserQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.add_user(");
        builder.append(data.uid);
        builder.append(");");
        return builder.toString();
    }
}
