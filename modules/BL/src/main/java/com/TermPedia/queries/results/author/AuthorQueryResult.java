package com.TermPedia.queries.results.author;

import lombok.Data;

import java.util.List;

@Data
public class AuthorQueryResult {
    private final List<String> authors;
}
