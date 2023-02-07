package com.TermPedia.queries.instances.terms;

import com.TermPedia.dto.Term;
import com.TermPedia.queries.QueryResult;

import java.util.Vector;

public class TermQueryResult extends QueryResult {
    private final Vector<Term> terms;
    public TermQueryResult(Vector<Term> terms) { this.terms = terms; }
    public Vector<Term> getTerms() { return terms; }
}
