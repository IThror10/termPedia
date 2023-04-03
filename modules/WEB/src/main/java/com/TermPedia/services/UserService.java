package com.TermPedia.services;

import com.TermPedia.commands.events.data.RegisterEvent;
import com.TermPedia.commands.result.EventResult;
import com.TermPedia.commands.user.AuthorizeCommand;
import com.TermPedia.commands.user.ChangeContactsCommand;
import com.TermPedia.dto.users.UserPrivateData;
import com.TermPedia.commands.user.*;
import com.TermPedia.handlers.CommandHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.user.GetUserPublicDataQuery;
import com.TermPedia.responses.item.EventResponse;
import com.TermPedia.responses.user.AuthenticationResponse;
import com.TermPedia.responses.user.UserPublicDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;

    public EventResponse register(RegisterEvent event) {
        CommandHandler handler = new CommandHandler();
        handler.handle(event);

        EventResult data = event.getResult();
        return new EventResponse(data.getLogin());
    }

    public UserPublicDataResponse changePublicData(ChangeContactsCommand command) {
        CommandHandler handler = new CommandHandler();
        handler.handle(command);
        return new UserPublicDataResponse(command.getResult().getData());
    }

    public AuthenticationResponse login(AuthorizeCommand command) {
        CommandHandler handler = new CommandHandler();
        handler.handle(command);

        UserPrivateData data = command.getResult().getData();
        String token = jwtService.generateToken(data);
        return new AuthenticationResponse(token, new UserPublicDataResponse(data));
    }

    public void logout(LogoutCommand command) {
        CommandHandler handler = new CommandHandler();
        handler.handle(command);
    }

    public UserPublicDataResponse getUserPublicData(GetUserPublicDataQuery query) {
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

