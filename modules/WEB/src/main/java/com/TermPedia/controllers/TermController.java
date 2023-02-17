package com.TermPedia.controllers;

import com.TermPedia.commands.events.data.AddLitToTermEvent;
import com.TermPedia.commands.events.data.AddTagToTermEvent;
import com.TermPedia.commands.events.data.AddTermEvent;
import com.TermPedia.dto.term.Term;
import com.TermPedia.queries.lit.FindLitByTermIdQuery;
import com.TermPedia.queries.tags.FindTagByTermIdQuery;
import com.TermPedia.queries.terms.FindTermByNameQuery;
import com.TermPedia.requests.term.AddLitToTermRequest;
import com.TermPedia.requests.term.AddTagToTermRequest;
import com.TermPedia.requests.term.AddTermRequest;
import com.TermPedia.responses.item.RatedTagResponse;
import com.TermPedia.responses.lit.RatedLiteratureResponse;
import com.TermPedia.responses.term.TermsResponse;
import com.TermPedia.services.TermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/terms")
@RequiredArgsConstructor
public class TermController {
    private final TermService service;

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
    @PostMapping(value = "/{termId}/litList", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity addLitToTerm(
            @RequestBody AddLitToTermRequest request,
            @RequestAttribute("uid") Integer userId,
            @PathVariable Integer termId
    ) {
        AddLitToTermEvent event = new AddLitToTermEvent(
                termId,
                request.litId(),
                userId
        );

        service.addLitToTerm(event);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Add tag-term connection")
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
    @PostMapping(value = "/{termId}/tagsList", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity addTagToTerm(
            @RequestBody AddTagToTermRequest request,
            @RequestAttribute("uid") Integer userId,
            @PathVariable Integer termId
    ) {
        AddTagToTermEvent event = new AddTagToTermEvent(
                termId,
                request.tag(),
                userId
        );

        service.addTagToTerm(event);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Get Terms")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found Terms",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TermsResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content)
    })
    @GetMapping(produces = { "application/json" })
    public ResponseEntity getTagByTerm(
            @RequestParam(name="term_search_name") String name,
            @RequestParam(name="term_search_amount", required = false, defaultValue = "10") int getAmount,
            @RequestParam(name="term_search_page", required = false, defaultValue = "1") int getPage
    ) {
        FindTermByNameQuery query = new FindTermByNameQuery(
                getAmount,
                getAmount * (getPage - 1),
                name
        );

        TermsResponse response = service.getTerms(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create Term")
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
    @PostMapping(produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity createTerm(
            @RequestBody AddTermRequest request,
            @RequestAttribute("uid") Integer userId
    ) {
        Term term = new Term(
                request.name(),
                request.description()
        );
        AddTermEvent event = new AddTermEvent(
                userId,
                term
        );

        service.addTerm(event);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Get Tags By Term")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found Tags",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RatedTagResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content)
    })
    @GetMapping(value = "/{termId}/tagsList", produces = { "application/json" })
    public ResponseEntity getTagByTerm(
            @PathParam(value = "termId") Integer termId,
            @RequestParam(name="tag_search_amount", required = false, defaultValue = "10") int getAmount,
            @RequestParam(name="tag_search_page", required = false, defaultValue = "1") int getPage,
            @RequestParam(name="search_new", required = false, defaultValue = "false") boolean searchNew
    ) {
        FindTagByTermIdQuery query = new FindTagByTermIdQuery(
                getAmount,
                getAmount * (getPage - 1),
                termId,
                searchNew
        );

        RatedTagResponse response = service.getTagsByTerm(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Lit By Term")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found Lit",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RatedLiteratureResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request",
                    content = @Content)
    })
    @GetMapping(value = "/{termId}/litList", produces = { "application/json" })
    public ResponseEntity getLitByTerm(
            @PathParam(value = "termId") Integer termId,
            @RequestParam(name="lit_search_amount", required = false, defaultValue = "10") int getAmount,
            @RequestParam(name="lit_search_page", required = false, defaultValue = "1") int getPage
    ) {
        FindLitByTermIdQuery query = new FindLitByTermIdQuery(
            getAmount,
            getAmount * (getPage - 1),
            termId
        );

        RatedLiteratureResponse response = service.getLitByTerm(query);
        return ResponseEntity.ok(response);
    }
}
