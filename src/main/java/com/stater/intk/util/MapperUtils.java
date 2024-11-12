package com.stater.intk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stater.intk.model.adapter.InstantTypeAdapter;
import com.stater.intk.model.adapter.LocalDateTypeAdapter;
import com.stater.intk.model.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;

import static java.util.Objects.isNull;

@Slf4j
public class MapperUtils {
    private MapperUtils() {
    }

    private final static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

    public static <T> T objectToObject(Object value, Type valueType) {
        return stringToObject(objectToString(value), valueType);
    }

    public static String objectToString(Object value) {
        if (isNull(value)) throw new BusinessException("Error en el mapeo.");
        try {
            return gson.toJson(value);
        } catch (Exception e) {
            throw new BusinessException("Error en el mapeo.");
        }
    }

    public static <T> T stringToObject(String content, Type valueType) {
        if (isNull(content) || content.isEmpty()) throw new BusinessException("Error en el mapeo.");
        try {
            return gson.fromJson(content, valueType);
        } catch (Exception e) {
            throw new BusinessException("Error en el mapeo.");
        }
    }
}
