package com.TermPedia.factory.query;

import com.TermPedia.factory.adapters.ISearchAdapter;
import com.TermPedia.factory.command.EventData;
import com.TermPedia.factory.command.common.ISynchronizer;
import com.TermPedia.factory.query.common.UpdateRequests;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class StatementUpdater implements IUpdater {
    private final ISearchAdapter searcher;
    private final UpdateRequests builder;
    private ISynchronizer synchronizer;
    private final static Logger logger;
    static {
        logger = Logger.getLogger("QueryDB");
    }

    public StatementUpdater(ISearchAdapter searcher, @NotNull UpdateRequests builder) {
        this.searcher = searcher;
        this.builder = builder;
    }

    @Override
    public boolean update() {
        try {
            synchronizer.hasNewRows();
            if (!synchronizer.hasNewRows())
                return true;

            for (EventData data = synchronizer.getEventData(); data != null; data = synchronizer.getEventData()) {
                String query = createQuery(data);

                try {
                    searcher.execute(query);
                } catch (Exception e) {
                    logger.warning(e.getMessage());
                } finally {
                    synchronizer.updated();
                }
            }
            synchronizer.freeUsed();
            return true;

        } catch (Exception e) {
            logger.warning(e.getMessage());
            return false;
        }
    }

    private String createQuery(EventData data) {
        return switch (data.type) {
            case registration -> builder.newUserQuery(data);
            case change_term_lit_rating -> builder.newRateTermLitQuery(data);
            case change_term_tag_rating -> builder.newRateTermTagQuery(data);
            case new_term -> builder.newTermQuery(data);
            case new_lit -> builder.newLitQuery(data);
            case new_term_tag -> builder.newTagTermPareQuery(data);
            case new_term_lit -> builder.newLitTermPareQuery(data);
        };
    }
    @Override
    public void setSynchronizer(ISynchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }
}
