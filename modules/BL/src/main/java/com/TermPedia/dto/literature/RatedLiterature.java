package com.TermPedia.dto.literature;

import com.TermPedia.dto.exceptions.FormatException;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@ToString
@Getter
public class RatedLiterature extends TagLiterature {
    private Integer ratesAmount;
    public RatedLiterature(
            @NotNull Integer lid,
            @NotNull String name,
            @NotNull String type,
            @Nullable Integer year,
            @NotNull List<String> authors,
            @Nullable Double rating,
            @Nullable Integer ratesAmount
    ) throws FormatException {
        super(lid, name, type, year, authors, rating);
        this.ratesAmount = ratesAmount == null ? 0 : ratesAmount;
    }
}