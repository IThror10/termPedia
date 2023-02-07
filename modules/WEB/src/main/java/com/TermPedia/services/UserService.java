package com.TermPedia.services;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.NotFoundException;
import com.TermPedia.dto.users.UserPublicData;
import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.handlers.EventHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.instances.users.GetUserPublicDataQuery;
import com.TermPedia.securityDTO.AuthenticationResponse;
import com.TermPedia.securityDTO.ExtendedUser;
import com.TermPedia.securityDTO.LoginRequest;
import com.TermPedia.securityDTO.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class  UserService implements UserDetailsService {

    private final JwtService jwtService;

    public UserService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserPublicData data = getUserPublicData(username);
            return new ExtendedUser(data);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
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

//        UserDetails details = new ExtendedUser(event.getResult().getUserData());
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

//        UserDetails details = new ExtendedUser(event.getResult().getUserData());
        String token = jwtService.generateToken(event.getResult().getUserData());
        return new AuthenticationResponse(token);
    }
}

