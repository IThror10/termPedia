package com.TermPedia.requests.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MarkRequest (
    @NotBlank @JsonProperty("termId") @Schema(example = "1") Integer termId,
    @NotBlank @JsonProperty("mark") @Schema(example = "5", minimum = "0", maximum = "5") Integer mark
) {}