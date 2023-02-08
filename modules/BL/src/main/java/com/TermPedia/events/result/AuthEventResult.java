package com.TermPedia.events.result;

import com.TermPedia.dto.users.UserPrivateData;

public class AuthEventResult extends EventResult {
    private final UserPrivateData data;

    public AuthEventResult(UserPrivateData data) {
        this.data = data;
    }

    public UserPrivateData getUserData() {
        return data;
    }
}
