package com.TermPedia.queries.books;

import com.TermPedia.dto.RatedBook;
import com.TermPedia.queries.QueryResult;

import java.util.Vector;

public class RatedBookQueryResult extends QueryResult {
    private final Vector<RatedBook> books;
    public RatedBookQueryResult(Vector<RatedBook> books) { this.books = books; }
    public Vector<RatedBook> getBooks() { return books; }
}
