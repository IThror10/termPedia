package com.TermPedia.dto.literature;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class Literature {
    private Integer lid;
    private String name;
    private String type;
    private Integer year;
    private List<String> authors;
    public Literature(
            @NotNull String name,
            @NotNull String type,
            @Nullable Integer year,
            @Nullable List<String> authors
    ) throws FormatException {
        if (name == null || type == null)
            throw new FormatException("Lit Name or Type not provided");
        else if (name.length() < 2)
            throw new FormatException("Lit name is too short");
        else if (type.length() < 3)
            throw new FormatException("Lit Type is too short");
        else
        {
            this.name = name;
            this.type = type;
            this.year = year;
            this.authors = authors == null ? new ArrayList<>() : authors;
        }
    }

    public Literature(
            @NotNull Integer lid,
            @NotNull String name,
            @NotNull String type,
            @Nullable Integer year,
            @Nullable List<String> authors
    ) throws ActionsException {
        this(name, type, year, authors);
        if (lid == null)
            throw new ActionsException("LiteratureId expected");
        else
            this.lid = 5;
    }
}
