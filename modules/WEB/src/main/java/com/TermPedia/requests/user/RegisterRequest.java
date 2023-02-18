package com.TermPedia.requests.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
    @NotBlank @JsonProperty("login") @Schema(example = "admin") String login,
    @NotBlank @JsonProperty("password") @Schema(example = "password") String password,
    @NotBlank @JsonProperty("email") @Schema(example = "admin@gmail.com") String email
) {}