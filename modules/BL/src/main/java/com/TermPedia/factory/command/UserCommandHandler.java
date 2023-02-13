package com.TermPedia.factory.command;

import com.TermPedia.commands.events.data.RegisterEvent;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.commands.result.*;
import com.TermPedia.commands.user.*;
import com.TermPedia.queries.user.GetUserPublicDataQuery;
import com.TermPedia.queries.results.user.UserPublicDataQueryResult;
import org.jetbrains.annotations.NotNull;

public interface UserCommandHandler {
    EventResult register(@NotNull RegisterEvent event) throws ActionsException;
    EventResult logout(@NotNull LogoutCommand event) throws ActionsException;
    AuthCommandResult authorize(@NotNull AuthorizeCommand event) throws ActionsException;
    ValidateCommandResult validate(@NotNull ValidateCommand event) throws ActionsException;
    ContactDataEventResult changeContactData(@NotNull ChangeContactsCommand event) throws ActionsException;
    UserPublicDataQueryResult getInfo(@NotNull GetUserPublicDataQuery query) throws ActionsException;
}
