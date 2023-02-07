package com.TermPedia.controllers;


import com.TermPedia.securityDTO.AuthenticationResponse;
import com.TermPedia.securityDTO.LoginRequest;
import com.TermPedia.securityDTO.RegisterRequest;
import com.TermPedia.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
//    private final static ResponseEntity<Object> UNAUTHORIZED =
//            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //    public ResponseEntity login(ServerWebExchange swe) {
//        return ResponseEntity.ok(swe.getRequest().toString());
//        Mono<MultiValueMap<String, String>> formData = swe.getFormData();
//        formData.flatMap(credentials ->
//
//        );
//        return swe.getFormData().flatMap(credentials -> {
//            User user = userService.authorize(credentials.getFirst("username"), credentials.getFirst("password"));
//            if (user != null)
//                return ResponseEntity.ok(jwtUtil.generateToken(user));
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        })
//        }).defaultIfEmpty());
//    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Map<String, String> message) {
        LoginRequest request = new LoginRequest(
                message.get("login"),
                message.get("password")
        );
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody Map<String, String> message) {
        RegisterRequest request = new RegisterRequest(
                message.get("login"),
                message.get("password"),
                message.get("email")
        );
        return ResponseEntity.ok(userService.register(request));
    }
}
