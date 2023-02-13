package com.TermPedia.responses.item;

import com.TermPedia.dto.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TagResponse {
    @Schema(example = "[{name : IT}]")
    public final List<Tag> tags;
}
