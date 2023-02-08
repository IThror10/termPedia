package com.TermPedia.factory.command.common;

import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.LogoutEvent;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.events.user.ValidateEvent;
import com.TermPedia.queries.instances.users.GetUserPublicDataQuery;

public interface IReqAuthHandlerRequests {
    String registerEventQuery(RegisterEvent event);
    String authorizeEventQuery(AuthorizeEvent event);
    String validEventQuery(ValidateEvent event);
    String logoutEventQuery(LogoutEvent event);
    String getUserPublicDataQuery(GetUserPublicDataQuery query);
}
