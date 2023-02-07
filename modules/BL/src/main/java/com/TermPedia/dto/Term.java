package com.TermPedia.dto;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Term implements IMappable {
    public final String name;
    public final String description;
    public Term(@NotNull String name, @NotNull String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + "; Description : " + description;
    }

    @Override
    public Map<String, String> toMap() {
        return new HashMap<String, String>() {
            { put("name", name); put("description", description); }
        };
    }
}
