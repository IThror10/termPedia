package com.TermPedia.factory.command.common;


public interface ISyncRequests {
    String syncNewRowsQuery();
    String syncDeleteCompletedQuery();
}
