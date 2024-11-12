package com.stater.intk.common.utils;

import java.util.UUID;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public class GeneralUtils {

    public static String generateId(int limit, boolean isUpperCase) {
        String id = UUID.randomUUID().toString();
        while (id.contains("-")) id = id.replace("-", "");
        id = id.substring(0, limit);
        id = isUpperCase ? id.toUpperCase() : id.toLowerCase();
        return id;
    }

    public static <T> String getStringOfObject(T value, Function<T, String> mapper) {
        return ofNullable(value)
                .map(mapper)
                .orElse(null);
    }
}
