package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.command.EventData;

public interface UsersRequests {
    String addUserQuery(EventData data) throws ActionsException;
}
