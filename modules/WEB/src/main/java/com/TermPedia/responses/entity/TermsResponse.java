package com.TermPedia.responses.entity;

import com.TermPedia.dto.term.Term;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record TermsResponse(
    @NotBlank List<Term> terms
) {  }
