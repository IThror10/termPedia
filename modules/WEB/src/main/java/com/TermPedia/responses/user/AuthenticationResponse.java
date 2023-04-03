package com.TermPedia.responses.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationResponse(
        @NotBlank @Schema(example = "SomeString.WithAccess.ToSecuredApi") String token,
        @NotBlank UserPublicDataResponse user
) {}