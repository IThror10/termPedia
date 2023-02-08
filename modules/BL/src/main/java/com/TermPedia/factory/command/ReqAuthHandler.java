package com.TermPedia.factory.command;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.events.result.AuthEventResult;
import com.TermPedia.events.result.EventStatus;
import com.TermPedia.events.result.ValidateEventResult;
import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.LogoutEvent;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.events.user.ValidateEvent;
import com.TermPedia.queries.instances.users.GetUserPublicDataQuery;
import com.TermPedia.queries.instances.users.UserPublicDataQueryResult;
import org.jetbrains.annotations.NotNull;

public interface ReqAuthHandler {
    AuthEventResult register(@NotNull RegisterEvent event) throws ActionsException;
    AuthEventResult authorize(@NotNull AuthorizeEvent event) throws ActionsException;
    ValidateEventResult validate(@NotNull ValidateEvent event) throws ActionsException;
    EventStatus logout(@NotNull LogoutEvent event) throws ActionsException;
    UserPublicDataQueryResult getInfo(@NotNull GetUserPublicDataQuery query) throws ActionsException;
}
