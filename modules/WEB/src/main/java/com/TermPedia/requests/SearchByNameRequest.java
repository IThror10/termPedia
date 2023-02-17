package com.TermPedia.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.Nullable;

public record SearchByNameRequest(
    @NotBlank @JsonProperty("name") @Schema(example = "Some name") String name,
    @Nullable @JsonProperty("page") @Schema(example = "1", minimum = "1", defaultValue = "1") Integer page,
    @NotBlank @JsonProperty("amount") @Schema(example = "10", minimum = "1", defaultValue = "10") Integer amount
) {}