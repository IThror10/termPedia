package com.TermPedia.factory.query;

import com.TermPedia.factory.command.common.ISynchronizer;

public interface IUpdater {
    boolean update() throws Exception;
    void setSynchronizer(ISynchronizer synchronizer);
}
