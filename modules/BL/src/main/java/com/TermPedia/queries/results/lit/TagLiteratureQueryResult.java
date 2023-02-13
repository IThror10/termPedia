package com.TermPedia.queries.results.lit;

import com.TermPedia.dto.literature.TagLiterature;
import lombok.Data;

import java.util.List;

@Data
public class TagLiteratureQueryResult {
    private final List<TagLiterature> books;
}
