package com.TermPedia.requests.term;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AddTermRequest(
    @NotBlank @JsonProperty("name") @Schema(example = "OOP") String name,
    @NotBlank @JsonProperty("description") @Schema(example = "Object Oriented Programming") String description
) {}