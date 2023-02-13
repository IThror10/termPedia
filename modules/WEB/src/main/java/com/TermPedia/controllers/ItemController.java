package com.TermPedia.controllers;

import com.TermPedia.responses.item.AuthorResponse;
import com.TermPedia.responses.item.LitTypesResponse;
import com.TermPedia.responses.item.TagResponse;
import com.TermPedia.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "Search lit types by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get litTypes",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LitTypesResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request body",
                    content = @Content)})
    @GetMapping(value = "/litType", produces = { "application/json" })
    public ResponseEntity getLitTypes(
            @RequestParam(name="get_lit_type") String name,
            @RequestParam(name="get_lit_type_amount", required = false, defaultValue = "10") int get,
            @RequestParam(name="get_lit_type_skip", required = false, defaultValue = "0") int skip
    ) {
        LitTypesResponse response = itemService.getLitTypes(name, get, skip);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search tags by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get tags",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TagResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request body",
                    content = @Content)})
    @GetMapping(value = "/tag", produces = { "application/json" })
    public ResponseEntity getTags(
            @RequestParam(name="get_tag") String name,
            @RequestParam(name="get_tag_amount", required = false, defaultValue = "10") int get,
            @RequestParam(name="get_tag_skip", required = false, defaultValue = "0") int skip
    ) {
        TagResponse response = itemService.getTags(name, get, skip);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search authors by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get authors",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Wrong request body",
                    content = @Content)})
    @GetMapping(value = "/author", produces = { "application/json" })
    public ResponseEntity getAuthors(
            @Schema(example = "qwerty") @RequestParam(name="get_author") String name,
            @RequestParam(name="get_author_amount", required = false, defaultValue = "10") int get,
            @RequestParam(name="get_author_skip", required = false, defaultValue = "0") int skip
    ) {
        AuthorResponse response = itemService.getAuthors(name, get, skip);
        return ResponseEntity.ok(response);
    }
}
