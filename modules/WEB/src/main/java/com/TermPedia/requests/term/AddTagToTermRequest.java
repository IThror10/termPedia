package com.TermPedia.requests.term;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AddTagToTermRequest(
    @NotBlank @JsonProperty("tagName") @Schema(example = "IT") String tag
) { }
