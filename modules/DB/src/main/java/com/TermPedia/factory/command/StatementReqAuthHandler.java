package com.TermPedia.factory.command;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.DataConflictException;
import com.TermPedia.dto.exceptions.NotFoundException;
import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.events.result.AuthEventResult;
import com.TermPedia.events.result.EventStatus;
import com.TermPedia.factory.adapters.ISearchAdapter;
import com.TermPedia.factory.command.common.IReqAuthHandlerRequests;
import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.dto.users.User;
import com.TermPedia.queries.instances.users.GetUserPublicDataQuery;
import com.TermPedia.queries.instances.users.UserPublicDataQueryResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class StatementReqAuthHandler implements ReqAuthHandler {
    private final ISearchAdapter searcher;
    private final IReqAuthHandlerRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("CommandDB"); }
    public StatementReqAuthHandler(@NotNull ISearchAdapter searcher, @NotNull IReqAuthHandlerRequests builder) {
        this.searcher = searcher;
        this.builder = builder;
    }


    @Override
    public AuthEventResult authorize(@NotNull AuthorizeEvent event) throws ActionsException {
        String query = builder.authorizeEventQuery(event);
        try {
            UserPublicData data = null;
            if (searcher.execute(query) && searcher.next())
                data = getUserPublicData();
            if (data == null || data.userID() == -1)
                throw new ActionsException("Wrong Login/Password" + event.getData());
            return new AuthEventResult(data);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public AuthEventResult register(@NotNull RegisterEvent event) throws ActionsException {
        String query = builder.registerEventQuery(event);
        try {
            if (searcher.execute(query)) {
                searcher.next();
                Integer userId = searcher.getInt("uid");
                switch (userId) {
                    case -1:
                        throw new ActionsException("This Login is Already Used");

                    case -2:
                        throw new ActionsException("This Email is Already Used");

                    case -3:
                        throw new ActionsException("Wrong Email address");
                }
                return new AuthEventResult(getUserPublicData());
            }
            throw new DataConflictException("Email already used");
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public UserPublicDataQueryResult getInfo(@NotNull GetUserPublicDataQuery inputQuery) throws ActionsException {
        String query = builder.getUserPublicDataQuery(inputQuery);
        try {
            if (searcher.execute(query) && searcher.next())
                return new UserPublicDataQueryResult(getUserPublicData());

            throw new NotFoundException("User Not Found");
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later");
        }
    }

    private UserPublicData getUserPublicData() throws Exception {
        String[] roles = {"USER"};
        return new UserPublicData(
                searcher.getInt("uid"),
                searcher.getString("login"),
                searcher.getString("email"),
                searcher.getStringList("phone"),
                searcher.getStringList("post"),
                new ArrayList<>(Arrays.stream(roles).toList())
        );
    }
}
