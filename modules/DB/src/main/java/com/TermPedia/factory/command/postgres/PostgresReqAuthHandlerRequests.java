package com.TermPedia.factory.command.postgres;

import com.TermPedia.commands.events.data.RegisterEvent;
import com.TermPedia.commands.user.AuthorizeCommand;
import com.TermPedia.commands.user.ChangeContactsCommand;
import com.TermPedia.commands.user.*;
import com.TermPedia.factory.command.common.IReqAuthHandlerRequests;
import com.TermPedia.queries.user.GetUserPublicDataQuery;

public class PostgresReqAuthHandlerRequests implements IReqAuthHandlerRequests {
    public StringBuilder builder;
    public PostgresReqAuthHandlerRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String registerEventQuery(RegisterEvent event) {
        builder.setLength(0);
        builder.append("SELECT * FROM app.register_user('");
        builder.append(event.getData());
        builder.append("', '");
        builder.append(event.getDateTime());
        builder.append("', ");
        builder.append(event.getEventType().ordinal());
        builder.append(");");
        return builder.toString();
    }

    @Override
    public String authorizeEventQuery(AuthorizeCommand event) {
        builder.setLength(0);
        builder.append("SELECT * FROM app.authorize_user('");
        builder.append(event.getData());
        builder.append("');");
        return builder.toString();
    }

    @Override
    public String validEventQuery(ValidateCommand event) {
        builder.setLength(0);
        builder.append("SELECT * FROM app.validate('");
        builder.append(event.login);
        builder.append("', '");
        builder.append(event.secret);
        builder.append("');");
        return builder.toString();
    }

    @Override
    public String logoutEventQuery(LogoutCommand event) {
        builder.setLength(0);
        builder.append("SELECT app.logout(");
        builder.append(event.getUid());
        builder.append(") as login;");
        return builder.toString();
    }

    @Override
    public String getUserPublicDataQuery(GetUserPublicDataQuery query) {
        builder.setLength(0);
        builder.append("SELECT * FROM app.get_user_data('{\"Login\" : \"");
        builder.append(query.userLogin);
        builder.append("\"}');");
        return builder.toString();
    }

    @Override
    public String changeContactDataQuery(ChangeContactsCommand event) {
        builder.setLength(0);
        builder.append("SELECT * FROM app.update_contact_info(");
        builder.append(event.getUid());
        builder.append(", '");
        builder.append(event.operation);
        builder.append("', '");
        builder.append(event.column);
        builder.append("', '");
        builder.append(event.value);
        builder.append("');");
        return builder.toString();
    }
}
