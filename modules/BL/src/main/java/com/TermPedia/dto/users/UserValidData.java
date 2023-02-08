package com.TermPedia.dto.users;

public class UserValidData {
    private final Integer userId;
    private final String login;

    public UserValidData(Integer userId, String login) {
        this.userId = userId;
        this.login = login;
    }

    public Integer userId() {
        return userId;
    }

    public String login() {
        return login;
    }
}
