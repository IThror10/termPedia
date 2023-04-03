package com.TermPedia.queries.results.term;

import com.TermPedia.dto.term.Term;
import lombok.Data;

import java.util.List;


@Data
public class TermQueryResult {
    private final List<Term> terms;
}
