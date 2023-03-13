package com.TermPedia.dto.tags;

import com.TermPedia.dto.exceptions.FormatException;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@ToString
@Getter
public class Tag {
    private String name;
    public Tag(@NotNull String name) throws FormatException {
        if (name == null)
            throw new FormatException("Tag is not provided");
        else if (name.length() < 2)
            throw new FormatException("Tag Name is too short");
        else {
            this.name = name;
        }
    }
}
