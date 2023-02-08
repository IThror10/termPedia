package com.TermPedia;

import com.TermPedia.dto.users.UserPrivateData;
import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.events.result.AuthEventResult;

import java.util.ArrayList;

public class CommandTestObjectsFactory {
    public static UserPrivateData getUserPrivateData() {
        return new UserPrivateData(
                "secret-string",
                "login",
                "email@yandex.ru",
                new ArrayList<String>(),
                new ArrayList<String>()
        );
    }

    public static AuthEventResult getAuthEventResult() {
        return new AuthEventResult(CommandTestObjectsFactory.getUserPrivateData());
    }
}
