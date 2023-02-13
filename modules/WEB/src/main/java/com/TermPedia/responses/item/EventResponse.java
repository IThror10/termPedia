package com.TermPedia.responses.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class EventResponse {
    @Schema(example = "{login : user}")
    public final String login;
}
