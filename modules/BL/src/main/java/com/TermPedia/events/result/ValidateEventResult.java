package com.TermPedia.events.result;

import com.TermPedia.dto.users.UserValidData;

public class ValidateEventResult extends EventResult {
    private final UserValidData data;

    public ValidateEventResult(UserValidData data) {
        this.data = data;
    }

    public UserValidData getUserData() {
        return data;
    }
}
