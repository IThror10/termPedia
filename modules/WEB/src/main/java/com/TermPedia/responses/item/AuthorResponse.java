package com.TermPedia.responses.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Vector;

@RequiredArgsConstructor
public class AuthorResponse {
    @Schema(example = "[Uncle Sam]")
    public final List<String> authors;
}
