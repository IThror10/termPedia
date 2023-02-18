package com.TermPedia.controllers;

import com.TermPedia.commands.events.data.RegisterEvent;
import com.TermPedia.commands.user.AuthorizeCommand;
import com.TermPedia.commands.user.ChangeContactsCommand;
import com.TermPedia.commands.user.LogoutCommand;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.user.GetUserPublicDataQuery;
import com.TermPedia.requests.user.AuthorizeRequest;
import com.TermPedia.requests.user.DataChangeRequest;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema)
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
    public ResponseEntity register(@RequestBody RegisterRequest request) throws Exception {
        RegisterEvent event = new RegisterEvent(
                request.login(),
                request.password(),
                request.email()
        );

        EventResponse response = userService.register(event);
        return ResponseEntity.created(new URI("user/" + response.login)).build();
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
        ChangeContactsCommand command = new ChangeContactsCommand(
                userId,
                request.field(),
                request.op(),
                request.getValue()
        );

        UserPublicDataResponse data = userService.changePublicData(command);
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
        AuthorizeCommand command = new AuthorizeCommand(
                request.login(),
                request.password()
        );

        AuthenticationResponse response = userService.login(command);
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
        LogoutCommand command = new LogoutCommand(userId);

        userService.logout(command);
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
                    content = @Content
            )
    })
    @GetMapping(value = "/{userLogin}", produces = { "application/json" })
    public ResponseEntity getUser(@PathVariable String userLogin) {
        GetUserPublicDataQuery query = new GetUserPublicDataQuery(userLogin);

        UserPublicDataResponse response = userService.getUserPublicData(query);
        return ResponseEntity.ok(response);
    }
}
