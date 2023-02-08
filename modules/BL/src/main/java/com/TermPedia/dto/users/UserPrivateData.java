package com.TermPedia.dto.users;

import java.util.Collection;
import java.util.List;

public class UserPrivateData extends UserPublicData {
    private final String secret;

    public UserPrivateData(String secret, String login, String email, List<String> phones,
                           List<String> posts) {
        super(login, email, phones, posts);
        this.secret = secret;
    }

    public String secret() {
        return secret;
    }
}