package com.TermPedia.dto.tags;

import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ToString
@Getter
public class RatedTag extends Tag {
    private Double rating;
    private Integer ratesAmount;

    public RatedTag(
            @NotNull String name,
            @Nullable Double rating,
            @Nullable Integer ratesAmount
    ) {
        super(name);
        this.rating = rating;
        this.ratesAmount = ratesAmount == null ? 0 : ratesAmount;
    }
}
