package com.TermPedia.controllers;

import com.TermPedia.requests.term.AddLitToTermRequest;
import com.TermPedia.services.LitService;
import com.TermPedia.commands.events.data.*;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.dto.literature.Literature;

import com.TermPedia.queries.lit.*;
import com.TermPedia.queries.user.UserTermLitRatingQuery;
import com.TermPedia.queries.authors.FindAuthorByNameQuery;
import com.TermPedia.queries.litTypes.FindLitTypesByNameQuery;

import com.TermPedia.responses.lit.*;
import com.TermPedia.responses.item.*;
import com.TermPedia.responses.user.MarkResponse;
import com.TermPedia.requests.user.MarkRequest;
import com.TermPedia.requests.lit.AddLitRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/literature")
public class LiteratureController {
    private final LitService service;

    @Operation(summary = "Set User Term Lit Rating")
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
    @PostMapping(value = "/{litId}/userRating", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity rateTermLitConnection(
            @RequestBody MarkRequest request,
            @RequestAttribute("uid") Integer userId,
            @PathVariable Integer litId
    ) {
        ChangeTermLitRatingEvent event = new ChangeTermLitRatingEvent(
            userId,
            request.termId(),
            litId,
            request.mark()
        );

        service.changeTagLitRating(event);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Get User Term Lit Rating")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User's term-lit Rating",
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
                    description = "Lit or Lit-Term connection not found",
                    content = @Content
            )
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{litId}/userRating", produces = { "application/json" })
    public ResponseEntity getTermLitRating(
            @RequestParam(name="termId") Integer termId,
            @RequestAttribute("uid") Integer userId,
            @PathVariable Integer litId
    ) {
        UserTermLitRatingQuery query = new UserTermLitRatingQuery(
                userId,
                termId,
                litId
        );

        MarkResponse response = service.getLitTermRating(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Authors By Name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Authors",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthorResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content
            )
    })
    @GetMapping(value = "/authors", produces = { "application/json" })
    public ResponseEntity getAuthorsByName(
            @RequestParam(name="author_search_name") String search_name,
            @RequestParam(name="author_search_amount", required = false, defaultValue = "10") int search_amount,
            @RequestParam(name="author_search_page", required = false, defaultValue = "1") int search_page
    ) {
        FindAuthorByNameQuery query = new FindAuthorByNameQuery(
            search_amount,
            search_amount * (search_page - 1),
            search_name
        );

        AuthorResponse response = service.getAuthors(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get LitTypes By Name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "LitTypes",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LitTypesResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content
            )
    })
    @GetMapping(value = "/litTypes", produces = { "application/json" })
    public ResponseEntity getLitTypesByName(
            @RequestParam(name="type_search_name") String search_name,
            @RequestParam(name="type_search_amount", required = false, defaultValue = "10") int search_amount,
            @RequestParam(name="type_search_page", required = false, defaultValue = "1") int search_page
    ) {
        FindLitTypesByNameQuery query = new FindLitTypesByNameQuery(
            search_amount,
            search_amount * (search_page - 1),
            search_name
        );

        LitTypesResponse response = service.getLitTypes(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create new literature")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Accepted",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request body",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity addLit(@RequestBody AddLitRequest request, @RequestAttribute("uid") Integer userId) {
        Literature literature = new Literature(
                request.name(),
                request.type(),
                request.year(),
                Arrays.asList(request.authors())
        );
        AddLitEvent event = new AddLitEvent(
                userId,
                literature
        );

        service.addLiterature(event);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Add literature-term connection")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Accepted",
                    content = @Content),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/{litId}/terms", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity addLitToTerm(
            @RequestBody AddLitToTermRequest request,
            @RequestAttribute("uid") Integer userId,
            @PathVariable Integer litId
    ) {
        AddLitToTermEvent event = new AddLitToTermEvent(
                request.termId(),
                litId,
                userId
        );

        service.addLitToTerm(event);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Search literature")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found Literature",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LiteratureResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request body",
                    content = @Content)
    })
    @GetMapping(produces = { "application/json" })
    public ResponseEntity getLitByTags(
            @RequestParam(name="term_id", required = false) Integer termId,
            @RequestParam(name="term_name", required = false) String termName,
            @RequestParam(name="author_name", required = false) String authorName,
            @RequestParam(name="book_name", required = false) String bookName,

            @RequestParam(name="search_amount", required = false, defaultValue = "10") int getAmount,
            @RequestParam(name="search_page", required = false, defaultValue = "1") int getPage,

            @RequestParam(name="min_rating", required = false, defaultValue = "0") double minRating,
            @RequestParam(name="order_by_rating", required = false, defaultValue = "true") boolean orderByRating,
            @RequestParam(name="new_first", required = false, defaultValue = "false") boolean newFirst,

            @RequestParam(name="lit_type", required = false) String litType,
            @RequestParam(name="tags", required = false) String[] tags,
            @RequestParam(name="year_start", required = false, defaultValue = "0") Integer year_start,
            @RequestParam(name="year_end", required = false, defaultValue = "2030") Integer year_end
    ) {
        switch (
                (termName == null ? 0 : 1)
                + (authorName == null ? 0 : 2)
                + (bookName == null ? 0 : 4)
                + (termId == null ? 0 : 8)
        ) {
            case 0 -> {
                FindLitByTagQuery query = new FindLitByTagQuery(
                        null, orderByRating, minRating, litType,
                        tags == null ? null : Arrays.asList(tags),
                        year_start, year_end, getAmount, getAmount * (getPage - 1)
                );
                TagLiteratureResponse response = service.searchByTags(query);
                return ResponseEntity.ok(response);
            }
            case 1 -> {
                FindLitByLikeTermNameQuery query = new FindLitByLikeTermNameQuery(
                        null, orderByRating, newFirst, minRating, litType, year_start,
                        year_end, tags == null ? null : Arrays.asList(tags),
                        getAmount, getAmount * (getPage - 1), termName
                );
                RatedLiteratureResponse response = service.searchByTerm(query);
                return ResponseEntity.ok(response);
            }
            case 2 -> {
                FindLitByAuthorNameQuery query = new FindLitByAuthorNameQuery(
                        null, litType, year_start, year_end,
                        getAmount, getAmount * (getPage - 1), authorName
                );
                LiteratureResponse response = service.searchByAuthor(query);
                return ResponseEntity.ok(response);
            }
            case 4 -> {
                FindLitByLikeNameQuery query = new FindLitByLikeNameQuery(
                        null, orderByRating, minRating, litType,
                        tags == null ? null : Arrays.asList(tags),
                        year_start, year_end, getAmount, getAmount * (getPage - 1), bookName
                );
                TagLiteratureResponse response = service.searchByName(query);
                return ResponseEntity.ok(response);
            }
            case 8 -> {
                FindLitByTermIdQuery query = new FindLitByTermIdQuery(
                        getAmount,
                        getAmount * (getPage - 1),
                        termId
                );

                RatedLiteratureResponse response = service.searchByTermId(query);
                return ResponseEntity.ok(response);
            }
            default -> throw new FormatException("Unknown input parameters combination");
        }
    }
}
