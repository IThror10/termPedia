package com.TermPedia.queries.instances.tags;

import com.TermPedia.dto.RatedTag;
import com.TermPedia.queries.QueryResult;

import java.util.Vector;

public class RatedTagQueryResult extends QueryResult {
    private final Vector<RatedTag> tags;
    public RatedTagQueryResult(Vector<RatedTag> tags) { this.tags = tags; }
    public Vector<RatedTag> getTags() { return tags; }
}
