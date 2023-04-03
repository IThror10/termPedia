package com.TermPedia.requests.lit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
public record AddLitRequest(
        @NotBlank @JsonProperty("name") @Schema(example = "1984") String name,
        @NotBlank @JsonProperty("type") @Schema(example = "Novel") String type,
        @NotBlank @JsonProperty("year") @Schema(example = "1949") Integer year,
        @NotBlank @JsonProperty("authors") String[] authors
) {}