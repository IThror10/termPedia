package com.TermPedia.instances;

import com.TermPedia.factory.PostgresTestCommandConnection;
import com.TermPedia.factory.PostgresTestQueryConnection;
import com.TermPedia.factory.provider.ConstProvider;
import com.TermPedia.factory.command.SyncCommandFactory;
import com.TermPedia.factory.command.postgres.PostgresCommandFactory;
import com.TermPedia.factory.query.SyncQueryFactory;
import com.TermPedia.factory.query.postgres.PostgresQueryFactory;
import org.junit.jupiter.api.Test;

public class tagsTests {
    tagsTests() {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new PostgresTestQueryConnection());
        SyncQueryFactory.setProvider(new ConstProvider("postgres"));

        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new PostgresTestCommandConnection());
        SyncCommandFactory.setProvider(new ConstProvider("postgres"));
    }

    @Test
    public void ratedTagTest() {

    }

    @Test
    public void tagUserRatingTest() {

    }
}
