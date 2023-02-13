package com.TermPedia.responses.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LitTypesResponse {
    @Schema(example = "[Book, Media Book]")
    public final List<String> litTypes;
}
