package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.instances.IByNameGetSettings;
import com.TermPedia.queries.instances.IRatedGetSettings;
import com.TermPedia.queries.instances.tags.RatedTagQueryResult;
import com.TermPedia.queries.instances.tags.TagQueryResult;

public interface TagsSearcher {
    TagQueryResult getTagsByName(IByNameGetSettings settings) throws ActionsException;
    RatedTagQueryResult getTagsByTerm(IRatedGetSettings settings) throws ActionsException;
}
