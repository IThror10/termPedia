package com.TermPedia.queries.results.lit;

import com.TermPedia.dto.literature.RatedLiterature;
import lombok.Data;

import java.util.List;

@Data
public class RatedLiteratureQueryResult {
    private final List<RatedLiterature> books;
}
