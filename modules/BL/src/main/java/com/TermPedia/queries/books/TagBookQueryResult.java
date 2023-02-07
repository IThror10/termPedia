package com.TermPedia.queries.books;

import com.TermPedia.dto.TagBook;
import com.TermPedia.queries.QueryResult;

import java.util.Vector;

public class TagBookQueryResult extends QueryResult {
    private final Vector<TagBook> books;
    public TagBookQueryResult(Vector<TagBook> books) { this.books = books; }
    public Vector<TagBook> getBooks() { return books; }
}
