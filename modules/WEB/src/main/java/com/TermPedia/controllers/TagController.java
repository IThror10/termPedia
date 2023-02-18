package com.TermPedia.controllers;

import com.TermPedia.commands.events.data.ChangeTermTagRatingEvent;
import com.TermPedia.queries.tags.FindTagByNameQuery;
import com.TermPedia.queries.user.UserTermTagRatingQuery;
import com.TermPedia.requests.user.MarkRequest;
import com.TermPedia.responses.entity.TagResponse;
import com.TermPedia.responses.user.MarkResponse;
import com.TermPedia.services.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("tags")
public class TagController {
    private final TagService service;

    @Operation(summary = "Search Tags By Name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found tags",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TagResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content)
    })
    @GetMapping(produces = { "application/json" })
    public ResponseEntity getTags(
            @RequestParam(name="tag_search_name") String search_name,
            @RequestParam(name="tag_search_amount", required = false, defaultValue = "10") int search_amount,
            @RequestParam(name="tag_search_page", required = false, defaultValue = "1") int search_page
    ) {
        FindTagByNameQuery query = new FindTagByNameQuery(
                search_amount,
                search_amount * (search_page - 1),
                search_name
        );

        TagResponse response = service.findTagsByName(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Set User Term Tag Rating")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Accepted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/{tagName}/userRatings", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity rateTermTagConnection(
            @RequestBody MarkRequest request,
            @RequestAttribute("uid") Integer userId,
            @PathVariable String tagName
    ) {
        ChangeTermTagRatingEvent event = new ChangeTermTagRatingEvent(
                userId,
                request.termId(),
                tagName,
                request.mark()
        );

        service.changeTagTermRating(event);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Get User Term Tag Rating")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User's term-tag Rating",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MarkResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tag or Tag-Term connection not found",
                    content = @Content
            )
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{tagName}/userRatings", produces = { "application/json" })
    public ResponseEntity getTermTagRating(
            @RequestParam(name="termId") Integer termId,
            @RequestAttribute("uid") Integer userId,
            @PathVariable String tagName
    ) {
        UserTermTagRatingQuery query = new UserTermTagRatingQuery(
                userId,
                termId,
                tagName
        );

        MarkResponse response = service.getTagTermRating(query);
        return ResponseEntity.ok(response);
    }
}
