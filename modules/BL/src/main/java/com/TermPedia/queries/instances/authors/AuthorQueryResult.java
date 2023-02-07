package com.TermPedia.queries.instances.authors;

import com.TermPedia.queries.QueryResult;

import java.util.Vector;

public class AuthorQueryResult extends QueryResult {
    private final Vector<String> authors;
    public AuthorQueryResult(Vector<String> authors) { this.authors = authors; }
    public Vector<String> getAuthors() { return authors; }
}
