package com.TermPedia.services;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.LogoutEvent;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.events.user.ValidateEvent;
import com.TermPedia.handlers.EventHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.instances.users.GetUserPublicDataQuery;
import com.TermPedia.requests.LogoutRequest;
import com.TermPedia.responses.AuthenticationResponse;
import com.TermPedia.requests.LoginRequest;
import com.TermPedia.requests.RegisterRequest;
import org.springframework.stereotype.Service;



@Service
public class UserService {
    private final JwtService jwtService;

    public UserService(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    public UserPublicData getUserPublicData(String username) throws ActionsException {
        GetUserPublicDataQuery query = new GetUserPublicDataQuery(username);
        QueryHandler handler = new QueryHandler();
        handler.handle(query);
        return query.getResult().getUserData();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        RegisterEvent event = new RegisterEvent(
                request.login(),
                request.password(),
                request.email()
        );

        EventHandler handler = new EventHandler();
        handler.handle(event);

        String token = jwtService.generateToken(event.getResult().getUserData());
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(LoginRequest request) {
        AuthorizeEvent event = new AuthorizeEvent(
                request.login(),
                request.password()
        );

        EventHandler handler = new EventHandler();
        handler.handle(event);

        String token = jwtService.generateToken(event.getResult().getUserData());
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse logout(LogoutRequest request) {
        LogoutEvent event = new LogoutEvent(request.userId());
        EventHandler handler = new EventHandler();
        handler.handle(event);
        return new AuthenticationResponse(event.getResult().getStatus() ? "true" : "false");
    }
    public boolean checkValid(ValidateEvent event) {
        EventHandler handler = new EventHandler();
        handler.handle(event);
        return event.getResult().getUserData().userId() != null;
    }
}

