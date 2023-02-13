package com.TermPedia.requests.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class AuthorizeRequest {

    public final String login;

    public final String password;

    public AuthorizeRequest(
            @NotBlank @JsonProperty("login") @Schema(example = "admin") String login,
            @NotBlank @JsonProperty("password") @Schema(example = "password") String password
    ) {
        this.login = login;
        this.password = password;
    }
}
