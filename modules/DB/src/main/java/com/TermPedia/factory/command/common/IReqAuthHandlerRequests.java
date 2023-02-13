package com.TermPedia.factory.command.common;

import com.TermPedia.commands.events.data.RegisterEvent;
import com.TermPedia.commands.user.AuthorizeCommand;
import com.TermPedia.commands.user.ChangeContactsCommand;
import com.TermPedia.commands.user.*;
import com.TermPedia.queries.user.GetUserPublicDataQuery;

public interface IReqAuthHandlerRequests {
    String registerEventQuery(RegisterEvent event);
    String authorizeEventQuery(AuthorizeCommand event);
    String validEventQuery(ValidateCommand event);
    String logoutEventQuery(LogoutCommand event);
    String getUserPublicDataQuery(GetUserPublicDataQuery query);
    String changeContactDataQuery(ChangeContactsCommand event);
}
