package com.TermPedia.services;

import com.TermPedia.commands.events.data.RegisterEvent;
import com.TermPedia.commands.result.EventResult;
import com.TermPedia.commands.user.AuthorizeCommand;
import com.TermPedia.commands.user.ChangeContactsCommand;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.users.UserPrivateData;
import com.TermPedia.commands.user.*;
import com.TermPedia.handlers.CommandHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.user.GetUserPublicDataQuery;
import com.TermPedia.requests.user.AuthorizeRequest;
import com.TermPedia.requests.user.DataChangeRequest;
import com.TermPedia.requests.user.LogoutRequest;
import com.TermPedia.requests.user.RegisterRequest;
import com.TermPedia.responses.item.EventResponse;
import com.TermPedia.responses.user.AuthenticationResponse;
import com.TermPedia.responses.user.UserPublicDataResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JwtService jwtService;

    public UserService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public EventResponse register(RegisterRequest request) {
        RegisterEvent event = new RegisterEvent(
                request.login,
                request.password,
                request.email
        );

        CommandHandler handler = new CommandHandler();
        handler.handle(event);

        EventResult data = event.getResult();
        return new EventResponse(data.getLogin());
    }

    public UserPublicDataResponse changePublicData(Integer userId, DataChangeRequest request) {
        ChangeContactsCommand event = new ChangeContactsCommand(
                userId,
                request.field(),
                request.op(),
                request.getValue()
        );

        CommandHandler handler = new CommandHandler();
        handler.handle(event);
        return new UserPublicDataResponse(event.getResult().getData());
    }

    public AuthenticationResponse login(AuthorizeRequest request) {
        AuthorizeCommand event = new AuthorizeCommand(
                request.login,
                request.password
        );

        CommandHandler handler = new CommandHandler();
        handler.handle(event);

        UserPrivateData data = event.getResult().getData();
        String token = jwtService.generateToken(data);
        return new AuthenticationResponse(token, new UserPublicDataResponse(data));
    }

    public void logout(LogoutRequest request) {
        LogoutCommand event = new LogoutCommand(
                request.userId()
        );

        CommandHandler handler = new CommandHandler();
        handler.handle(event);
    }

    public UserPublicDataResponse getUserPublicData(String userName) throws ActionsException {
        GetUserPublicDataQuery query = new GetUserPublicDataQuery(
                userName
        );

        QueryHandler handler = new QueryHandler();
        handler.handle(query);
        return new UserPublicDataResponse(query.getResult().getUser());
    }

    public boolean checkValid(ValidateCommand event) {
        CommandHandler handler = new CommandHandler();
        handler.handle(event);
        return event.getResult().getData().getUserId() != null;
    }
}

