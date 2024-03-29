package com.TermPedia.factory.provider;

import com.TermPedia.factory.provider.BaseProvider;
import org.jetbrains.annotations.NotNull;

public class EnvProvider implements BaseProvider {
    @Override
    public String getKey(@NotNull String key) throws NullPointerException {
        String name = System.getenv(key);
        if (name == null)
            throw new NullPointerException(key + "EnvVar not set");
        return name;
    }
}
