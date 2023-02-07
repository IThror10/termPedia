package com.TermPedia.factory.query.common;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.factory.command.EventData;
import com.TermPedia.queries.instances.IByNameGetSettings;
import com.TermPedia.queries.instances.IRatedGetSettings;

public interface TagsRequests {
    String getTagsByNameQuery(IByNameGetSettings settings) throws ActionsException;
    String getTagsByTermNameQuery(IRatedGetSettings settings) throws ActionsException;
    String addTagToTermQuery(EventData data);
    String rateTagTermQuery(EventData data);
}
