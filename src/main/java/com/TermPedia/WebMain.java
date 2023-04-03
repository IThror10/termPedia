package com.TermPedia;

import com.TermPedia.factory.provider.ConstProvider;
import com.TermPedia.factory.command.SyncCommandFactory;
import com.TermPedia.factory.command.postgres.PostgresCommandConnection;
import com.TermPedia.factory.command.postgres.PostgresCommandFactory;
import com.TermPedia.factory.provider.EnvProvider;
import com.TermPedia.factory.query.IUpdater;
import com.TermPedia.factory.query.SyncQueryFactory;
import com.TermPedia.factory.query.postgres.PostgresQueryConnection;
import com.TermPedia.factory.query.postgres.PostgresQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;
import java.util.logging.Logger;

@SpringBootApplication
public class WebMain {

    public static void main(String[] args) {
        //Command DB Init
        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new PostgresCommandConnection());
        PostgresCommandFactory.setProvider(new ConstProvider("postgres"));

        //Query DB Init
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new PostgresQueryConnection());
        PostgresQueryFactory.setProvider(new ConstProvider("postgres"));

        //Create Updater Thread
        start_sync_if_needed();

        //App Start Execution
        SpringApplication.run(WebMain.class, args);
    }

    private static void start_sync_if_needed() {
        if (Objects.equals(System.getenv("MAIN_STATUS"), "update"))
        {
            Thread sync = new Thread(() -> {
                try {
                    IUpdater updater = SyncQueryFactory.instance().createUpdater();
                    updater.setSynchronizer(SyncCommandFactory.instance().createSynchronizer());
                    while (true) {
                        Thread.sleep(5000);
                        try {
                            updater.update();
                        } catch (Exception err) {}
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            //App Start Execution
            sync.start();
        } else {
            Logger.getLogger("app").warning("No updater Set");
        }
    }

}
