package com.TermPedia.responses.term;

import com.TermPedia.dto.term.Term;

import java.util.List;

public record TermsResponse(
    List<Term> terms
) {  }
