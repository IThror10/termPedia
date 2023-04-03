package com.TermPedia.responses.user;

import com.TermPedia.dto.users.UserPublicData;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserPublicDataResponse {
    @NotBlank @Schema(example = "admin")
    public final String login;
    @NotBlank @Schema(example = "admin@gmail.com")
    public final String email;
    @NotBlank @Schema(example = "[212-85-0A, 212-85-0B]")
    public final List<String> phones;
    @NotBlank @Schema(example = "[BMSTU Student]")
    public final List<String> posts;

    public UserPublicDataResponse(String login, String email, List<String> phones, List<String> posts) {
        this.login = login;
        this.email = email;
        this.phones = phones;
        this.posts = posts;
    }

    public UserPublicDataResponse(UserPublicData data) {
        this.login = data.getLogin();
        this.email = data.getEmail();
        this.phones = data.getPhones();
        this.posts = data.getPosts();
    }
}
