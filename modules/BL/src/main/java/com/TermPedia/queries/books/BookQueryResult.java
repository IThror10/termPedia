package com.TermPedia.queries.books;

import com.TermPedia.dto.Book;
import com.TermPedia.queries.QueryResult;

import java.util.Vector;

public class BookQueryResult extends QueryResult {
    private final Vector<Book> books;
    public BookQueryResult(Vector<Book> books) { this.books = books; }
    public Vector<Book> getBooks() { return books; }
}
