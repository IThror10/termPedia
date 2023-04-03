package com.TermPedia.factory.provider;

import org.jetbrains.annotations.NotNull;

public interface BaseProvider {
    String getKey(@NotNull String key) throws NullPointerException;
}
