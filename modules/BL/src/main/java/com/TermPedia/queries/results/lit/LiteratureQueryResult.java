package com.TermPedia.queries.results.lit;

import com.TermPedia.dto.literature.Literature;
import lombok.Data;

import java.util.List;

@Data
public class LiteratureQueryResult {
    private final List<Literature> books;
}
