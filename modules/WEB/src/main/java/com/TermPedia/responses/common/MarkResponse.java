package com.TermPedia.responses.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MarkResponse(
        @NotBlank @Schema(example = "3") Integer termId,
        @NotBlank @Schema(example = "someId") Object id,
        @NotBlank @Schema(example = "4") Integer mark
) {  }
