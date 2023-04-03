package com.TermPedia.dto;

import com.TermPedia.dto.literature.Literature;
import com.TermPedia.dto.exceptions.FormatException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JsonBuilder implements DatumBuilder {
    private final StringBuilder builder;
    private boolean is_empty;

    public JsonBuilder() {
        this.builder = new StringBuilder(128);
        this.is_empty = true;
        builder.append("{}");
    }

    public JsonBuilder(int capacity) {
        this.builder = new StringBuilder(capacity);
        this.is_empty = true;
        builder.append("{}");
    }

    @Override
    public String getData() {
        return builder.toString();
    }


    @Override
    public DatumBuilder addInt(@NotNull String key, @Nullable Integer value) throws FormatException {
        changeBegin();
        appendStringInt(key, value);
        changeCommit();
        return this;
    }

    @Override
    public DatumBuilder addStr(@NotNull String key, @Nullable String value) throws FormatException {
        checkStringValue(value);
        changeBegin();
        appendStringString(key, value);
        changeCommit();
        return this;
    }

    @Override
    public DatumBuilder addLiterature(@NotNull Literature lit) throws FormatException {
        checkStringValue(lit.getName());
        checkStringValue(lit.getType());
        checkStringList(lit.getAuthors());

        changeBegin();
        appendBook(lit);
        changeCommit();
        return this;
    }

    private void appendBook(@NotNull Literature lit) {
        builder.append("\"Book\" : ");
        builder.append("{");
        appendStringString("Name", lit.getName());
        builder.append(", ");
        appendStringString("Type", lit.getType());
        builder.append(", ");
        appendStringInt("Year", lit.getYear());
        builder.append(", ");
        appendAuthorsList(lit.getAuthors());
        builder.append("}");
    }


    private void appendStringInt(@NotNull String str, @Nullable Integer num) {
        builder.append("\"");
        builder.append(str);
        builder.append("\" : ");
        builder.append(num == null ? "null" : num);
    }

    private void appendStringString(@NotNull String str1, @Nullable String str2) {
        builder.append("\"");
        builder.append(str1);
        if (str2 == null)
            builder.append("\" : null");
        else {
            builder.append("\" : \"");
            builder.append(str2);
            builder.append("\"");
        }
    }

    private void appendAuthorsList(@Nullable List<String> values) {
        boolean isFirst = true;

        builder.append("\"Authors\" : [");
        if (values != null) for (String value: values) {
            if (isFirst)
                isFirst = false;
            else
                builder.append(", ");

            if (value == null)
                builder.append("null");
            else {
                builder.append("\"");
                builder.append(value);
                builder.append("\"");
            }
        }
        builder.append("]");
    }

    private void changeBegin() {
        builder.setLength(builder.length() - 1);
        if (!is_empty)
            builder.append(", ");
    }

    private void changeCommit() {
        is_empty = false;
        builder.append("}");
    }

    private void checkStringList(@Nullable List<String> values) throws FormatException {
        if (values != null) for (String str : values)
            checkStringValue(str);
    }
    private void checkStringValue(@Nullable String str) throws FormatException {
        if (str == null)
            return;
        if (str.contains("\\") || str.contains("\"") || str.contains("/") || str.contains("?") || str.contains("'"))
            throw new FormatException("String contains forbidden symbol [`\\`, `\"`, `'`,`?`, `/`]");
    }
}
