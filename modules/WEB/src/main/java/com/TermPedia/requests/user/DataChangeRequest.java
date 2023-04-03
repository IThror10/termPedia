package com.TermPedia.requests.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class DataChangeRequest {
    private final Operation op;
    private final Field field;
    private final String value;

    public DataChangeRequest(
            @NotBlank @JsonProperty("op") @Schema(example = "add") Operation op,
            @NotBlank @JsonProperty("field") @Schema(example = "phone") Field field,
            @NotBlank @JsonProperty("value") @Schema(example = "212-85-06") String value
    ) {
        this.op = op;
        this.field = field;
        this.value = value;
    }

    public String op() {
        return op.name();
    }
    public String field() {
        return '/' + field.name();
    }
}

enum Operation { add, remove }
enum Field { post, phone }