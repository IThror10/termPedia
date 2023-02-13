package com.TermPedia.dto.term;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ToString
@Getter
public class Term {
    private Integer tid;
    private String name;
    private String description;
    public Term(
            @NotNull String name,
            @Nullable String description
    ) throws FormatException {
        if (name == null || description == null)
            throw new FormatException("Term Name or Description not provided");
        else if (name.length() < 2)
            throw new FormatException("Term name is too short");
        else {
            this.tid = null;
            this.name = name;
            this.description = description == null ? "" : description;
        }
    }

    public Term(
            @NotNull Integer tid,
            @NotNull String name,
            @Nullable String description
    ) throws ActionsException {
        this(name, description);
        if (tid == null)
            throw new ActionsException("Term ID expected");
        this.tid = tid;
    }
}
