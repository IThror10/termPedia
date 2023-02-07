package com.TermPedia.queries.instances.tags;

import com.TermPedia.dto.Tag;
import com.TermPedia.queries.QueryResult;

import java.util.Vector;

public class TagQueryResult extends QueryResult {
    private final Vector<Tag> tags;
    public TagQueryResult(Vector<Tag> tags) { this.tags = tags; }
    public Vector<Tag> getTags() { return tags; }
}
