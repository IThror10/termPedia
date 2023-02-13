package com.TermPedia.dto.tags;

import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@ToString
@Getter
public class Tag {
    private String name;
    public Tag(@NotNull String name) {
        this.name = name;
    }
}
