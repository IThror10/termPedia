package com.TermPedia.responses.item;

import com.TermPedia.dto.tags.RatedTag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RatedTagResponse {
    public final List<RatedTag> tags;
}
