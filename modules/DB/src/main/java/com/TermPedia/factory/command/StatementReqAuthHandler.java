package com.TermPedia.factory.command;

import com.TermPedia.factory.adapters.ISearchAdapter;
import com.TermPedia.factory.command.common.IReqAuthHandlerRequests;
import com.TermPedia.dto.exceptions.*;
import com.TermPedia.dto.users.*;

import com.TermPedia.commands.events.data.RegisterEvent;
import com.TermPedia.commands.result.*;
import com.TermPedia.commands.user.*;

import com.TermPedia.queries.user.GetUserPublicDataQuery;
import com.TermPedia.queries.results.user.UserPublicDataQueryResult;

import org.jetbrains.annotations.NotNull;
import java.util.logging.Logger;

public class StatementReqAuthHandler implements UserCommandHandler {
    private final ISearchAdapter searcher;
    private final IReqAuthHandlerRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("CommandDB"); }
    public StatementReqAuthHandler(@NotNull ISearchAdapter searcher, @NotNull IReqAuthHandlerRequests builder) {
        this.searcher = searcher;
        this.builder = builder;
    }

    @Override
    public EventResult register(@NotNull RegisterEvent event) throws ActionsException {
        String query = builder.registerEventQuery(event);
        try {
            searcher.execute(query);
            searcher.next();
            return switch (searcher.getInt("status")) {
                case -1 -> throw new DataConflictException("This Login is Already Used");
                case -2 -> throw new DataConflictException("This Email is Already Used");
                case -3 -> throw new FormatException("Wrong Email address format");
                default -> new EventResult(searcher.getString("login"));
            };
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }

    @Override
    public AuthCommandResult authorize(@NotNull AuthorizeCommand event) throws ActionsException {
        String query = builder.authorizeEventQuery(event);
        try {
            searcher.execute(query);
            searcher.next();

            if (searcher.getInt("status") == -1)
                throw new NotFoundException("Wrong email/password");

            return new AuthCommandResult(getUserPrivateData());
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }

    @Override
    public ValidateCommandResult validate(@NotNull ValidateCommand event) throws ActionsException {
        String query = builder.validEventQuery(event);
        try {
            searcher.execute(query);
            searcher.next();

            if (searcher.getInt("status") == -1)
                return new ValidateCommandResult(new UserValidData(null, null));

            return new ValidateCommandResult(new UserValidData(
                searcher.getInt("uid"),
                searcher.getString("login")
            ));
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }

    @Override
    public EventResult logout(@NotNull LogoutCommand event) throws ActionsException {
        String query = builder.logoutEventQuery(event);
        try {
            searcher.execute(query);
            searcher.next();
            return new EventResult(searcher.getString("login"));
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }

    @Override
    public ContactDataEventResult changeContactData(@NotNull ChangeContactsCommand event) throws ActionsException {
        String query = builder.changeContactDataQuery(event);
        try {
            searcher.execute(query);
            searcher.next();
            int status = searcher.getInt("status");
            return switch (status) {
                case -1 -> throw new NotFoundException("Change target not found");
                case -2 -> throw new NotFoundException("Operation not found");
                default -> new ContactDataEventResult(getUserPublicData());
            };
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
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
        } finally {
            searcher.closeConnection();
        }
    }

    private UserPrivateData getUserPrivateData() throws Exception {
        return new UserPrivateData(
                searcher.getString("secret"),
                searcher.getString("login"),
                searcher.getString("email"),
                searcher.getStringArray("phone"),
                searcher.getStringArray("post")
        );
    }
    private UserPublicData getUserPublicData() throws Exception {
        return new UserPublicData(
                searcher.getString("login"),
                searcher.getString("email"),
                searcher.getStringArray("phone"),
                searcher.getStringArray("post")
        );
    }
}
