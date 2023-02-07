package com.TermPedia.events.result;

import com.TermPedia.dto.users.UserPublicData;

public class AuthEventResult extends EventResult {
    private final UserPublicData data;

    public AuthEventResult(UserPublicData data) {
        this.data = data;
    }

    public UserPublicData getUserData() {
        return data;
    }
}
