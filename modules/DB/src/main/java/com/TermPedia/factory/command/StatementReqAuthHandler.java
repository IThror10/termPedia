package com.TermPedia.factory.command;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.DataConflictException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.dto.exceptions.NotFoundException;
import com.TermPedia.dto.users.UserPrivateData;
import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.dto.users.UserValidData;
import com.TermPedia.events.result.AuthEventResult;
import com.TermPedia.events.result.EventStatus;
import com.TermPedia.events.result.ValidateEventResult;
import com.TermPedia.events.user.LogoutEvent;
import com.TermPedia.events.user.ValidateEvent;
import com.TermPedia.factory.adapters.ISearchAdapter;
import com.TermPedia.factory.command.common.IReqAuthHandlerRequests;
import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.queries.instances.users.GetUserPublicDataQuery;
import com.TermPedia.queries.instances.users.UserPublicDataQueryResult;
import org.jetbrains.annotations.NotNull;

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
    public AuthEventResult register(@NotNull RegisterEvent event) throws ActionsException {
        String query = builder.registerEventQuery(event);
        try {
            searcher.execute(query);
            searcher.next();
            Integer status = searcher.getInt("status");
            switch (status) {
                case -1:
                    throw new DataConflictException("This Login is Already Used");

                case -2:
                    throw new DataConflictException("This Email is Already Used");

                case -3:
                    throw new FormatException("Wrong Email address format");

                default:
                    return new AuthEventResult(getUserPrivateData());
            }
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public AuthEventResult authorize(@NotNull AuthorizeEvent event) throws ActionsException {
        String query = builder.authorizeEventQuery(event);
        try {
            searcher.execute(query);
            searcher.next();

            int status = searcher.getInt("status");
            switch (status) {
                case -1:
                    throw new NotFoundException("Wrong email/password");

                default:
                    return new AuthEventResult(getUserPrivateData());
            }
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public ValidateEventResult validate(@NotNull ValidateEvent event) throws ActionsException {
        String query = builder.validEventQuery(event);
        try {
            searcher.execute(query);
            searcher.next();

            int status = searcher.getInt("status");
            switch (status) {
                case -1:
                    return new ValidateEventResult(new UserValidData(null, null));

                default:
                    return new ValidateEventResult(new UserValidData(
                        searcher.getInt("uid"),
                        searcher.getString("login")
                    ));
            }
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public EventStatus logout(@NotNull LogoutEvent event) throws ActionsException {
        String query = builder.logoutEventQuery(event);
        try {
            searcher.execute(query);
            searcher.next();

            return new EventStatus(searcher.getBoolean("status"));
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

    private UserPrivateData getUserPrivateData() throws Exception {
        return new UserPrivateData(
                searcher.getString("secret"),
                searcher.getString("login"),
                searcher.getString("email"),
                searcher.getStringList("phone"),
                searcher.getStringList("post")
        );
    }
    private UserPublicData getUserPublicData() throws Exception {
        return new UserPublicData(
                searcher.getString("login"),
                searcher.getString("email"),
                searcher.getStringList("phone"),
                searcher.getStringList("post")
        );
    }
}
