package com.TermPedia.dto.users;

import java.util.List;

public class UserPublicData {
    private final String login;
    private final String email;
    private final List<String> phones;
    private final List<String> posts;

    public UserPublicData(String login, String email, List<String> phones, List<String> posts) {
        this.login = login;
        this.email = email;
        this.phones = phones;
        this.posts = posts;
    }

    public UserPublicData() {
        this.login = null;
        this.email = null;
        this.phones = null;
        this.posts = null;
    }

    public String login() {
        return login;
    }
    public String email() {
        return email;
    }
    public List<String> phones() {
        return phones;
    }
    public List<String> posts() {
        return posts;
    }
}