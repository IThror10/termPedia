package com.TermPedia;

import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.events.result.AuthEventResult;

import java.util.ArrayList;

public class CommandTestObjectsFactory {
    public static UserPublicData getUserPublicData() {
        return new UserPublicData(
                2,
                "login",
                "email@yandex.ru",
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>()
        );
    }

    public static AuthEventResult getAuthEventResult() {
        return new AuthEventResult(CommandTestObjectsFactory.getUserPublicData());
    }
}
