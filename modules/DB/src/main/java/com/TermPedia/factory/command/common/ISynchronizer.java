package com.TermPedia.factory.command.common;

import com.TermPedia.factory.command.EventData;

public interface ISynchronizer {
    boolean hasNewRows() throws Exception;
    EventData getEventData() throws Exception;
    void updated() throws Exception;
    void freeUsed() throws Exception;
}
