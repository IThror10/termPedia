package com.TermPedia.queries.results.tag;

import com.TermPedia.dto.tags.RatedTag;
import lombok.Data;

import java.util.List;

@Data
public class RatedTagQueryResult {
    private final List<RatedTag> tags;
}
