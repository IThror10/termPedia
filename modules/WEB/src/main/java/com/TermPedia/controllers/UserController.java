package com.TermPedia.controllers;


import com.TermPedia.requests.LogoutRequest;
import com.TermPedia.responses.AuthenticationResponse;
import com.TermPedia.requests.LoginRequest;
import com.TermPedia.requests.RegisterRequest;
import com.TermPedia.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
//    private final static ResponseEntity<Object> UNAUTHORIZED =
//            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Map<String, String> message) {
        RegisterRequest request = new RegisterRequest(
                message.get("login"),
                message.get("password"),
                message.get("email")
        );
        return ResponseEntity.ok(userService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Map<String, String> message) {
        LoginRequest request = new LoginRequest(
                message.get("login"),
                message.get("password")
        );
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout(@RequestAttribute("uid") String userId) {
        Logger.getLogger("Web logger").warning(userId);
        LogoutRequest request = new LogoutRequest(Integer.parseInt(userId));
        return ResponseEntity.ok(userService.logout(request));
    }
}
