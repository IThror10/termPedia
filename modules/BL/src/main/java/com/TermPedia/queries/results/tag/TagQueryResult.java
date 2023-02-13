package com.TermPedia.queries.results.tag;

import com.TermPedia.dto.tags.Tag;
import lombok.Data;

import java.util.List;

@Data

public class TagQueryResult {
    private final List<Tag> tags;
}
