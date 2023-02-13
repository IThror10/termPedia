package com.TermPedia.controllers;

import com.TermPedia.commands.result.EventResult;
import com.TermPedia.requests.user.AuthorizeRequest;
import com.TermPedia.requests.user.DataChangeRequest;
import com.TermPedia.requests.user.LogoutRequest;
import com.TermPedia.requests.user.RegisterRequest;
import com.TermPedia.responses.item.EventResponse;
import com.TermPedia.responses.user.AuthenticationResponse;
import com.TermPedia.responses.user.UserPublicDataResponse;
import com.TermPedia.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User created",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EventResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request body",
                    content = @Content),
            @ApiResponse(
                    responseCode = "409",
                    description = "Login/Email already used",
                    content = @Content) })
    @PostMapping(produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity register(@RequestBody RegisterRequest request) {
        EventResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change User's public data")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Data Changed",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserPublicDataResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request body",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)})
    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping(produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity changeData(@RequestBody DataChangeRequest request, @RequestAttribute("uid") Integer userId) {
        UserPublicDataResponse data = userService.changePublicData(userId, request);
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Logs user into system")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User logged in",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request body",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Wrong Login/Password",
                    content = @Content) })
    @PostMapping(value = "/login", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity login(@RequestBody AuthorizeRequest request) {
        AuthenticationResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Closes User session")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User logged out",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)})
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/logout")
    public ResponseEntity logout(@RequestAttribute("uid") Integer userId) {
        LogoutRequest request = new LogoutRequest(userId);
        userService.logout(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Contact Information of User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User contact Data",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserPublicDataResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content)})
    @GetMapping(value = "/{userLogin}", produces = { "application/json" })
    public ResponseEntity getUser(@PathVariable String userLogin) {
        UserPublicDataResponse response = userService.getUserPublicData(userLogin);
        return ResponseEntity.ok(response);
    }
}
