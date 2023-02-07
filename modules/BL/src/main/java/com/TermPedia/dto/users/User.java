package com.TermPedia.dto.users;

import com.TermPedia.events.result.EventResult;
import org.jetbrains.annotations.NotNull;

public class User extends EventResult {
    public final String login;
    public final int UID;

    public User(@NotNull String login, int uid) {
        this.login = login;
        this.UID = uid;
    }

    @Override
    public String toString() { return login + UID; }

    public String getRole() {
        return "User";
    }
}
