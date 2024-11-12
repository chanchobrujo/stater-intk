package com.stater.intk.util;

import java.util.UUID;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public class GeneralUtil {

    public static String generateId(int limit) {
        String id = UUID.randomUUID().toString();
        while (id.contains("-")) id = id.replace("-", "");
        id = id.substring(0, limit);
        id = id.toUpperCase();
        return id;
    }

    public static <T> String getStringOfObject(T value, Function<T, String> mapper) {
        return ofNullable(value)
                .map(mapper)
                .orElse(null);
    }
}
