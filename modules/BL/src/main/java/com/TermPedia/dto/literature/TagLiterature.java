package com.TermPedia.dto.literature;

import com.TermPedia.dto.exceptions.ActionsException;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@ToString
@Getter
public class TagLiterature extends Literature {
    private Double rating;

    public TagLiterature (
            @NotNull Integer lid,
            @NotNull String name,
            @NotNull String type,
            @Nullable Integer year,
            @NotNull List<String> authors,
            @Nullable Double rating
    ) throws ActionsException {
        super(lid, name, type, year, authors);
        this.rating = rating;
    }
}
