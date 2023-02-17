package com.TermPedia.requests.lit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
public class AddLitRequest {
    public final String name;
    public final String type;
    public final Integer year;
    public final String[] authors;

    public AddLitRequest(
            @NotBlank @JsonProperty("name") @Schema(example = "1984") String name,
            @NotBlank @JsonProperty("type") @Schema(example = "Novel") String type,
            @NotBlank @JsonProperty("year") @Schema(example = "1949") Integer year,
            @NotBlank @JsonProperty("authors") String[] authors
    ) {
        this.name = name;
        this.type = type;
        this.year = year;
        this.authors = authors;
    }
}
