package com.TermPedia.dto;

import com.TermPedia.dto.literature.Literature;
import com.TermPedia.dto.exceptions.FormatException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DatumBuilder {
    String getData();
    DatumBuilder addInt(@NotNull String key, @Nullable Integer value) throws FormatException;
    DatumBuilder addStr(@NotNull String key, @Nullable String value) throws FormatException;
    DatumBuilder addLiterature(@NotNull Literature literature) throws FormatException;

}
