package com.stater.intk.common.utils;


import com.stater.intk.common.constants.DateConstants;

import java.time.*;

import static java.time.format.DateTimeFormatter.ofPattern;

public class DateUtils {
    public final static ZoneId ZONE_ID = ZoneId.of(DateConstants.ZONE_ID);

    public static OffsetDateTime offsetDateTimeNow() {
        Instant now = Instant.now();
        ZoneOffset offset = now
                .atZone(ZONE_ID)
                .getOffset();
        return now.atOffset(offset);
    }

    public static Instant instantNow() {
        return offsetDateTimeNow()
                .toInstant();
    }

    public static LocalDate localDateNow() {
        return LocalDate.now(ZONE_ID);
    }

    public static LocalDateTime localDateTimeNow() {
        return LocalDateTime.now()
                .atZone(ZONE_ID)
                .toLocalDateTime();
    }

    public static String localDateTimeFormatter(LocalDateTime fecha, String formato) {
        return fecha.format(ofPattern(formato));
    }

    public static String localDateFormatter(LocalDate fecha, String formato) {
        return fecha.format(ofPattern(formato));
    }

    public static String instantFormatter(Instant fecha, String formato) {
        return ofPattern(formato)
                .withZone(ZONE_ID)
                .format(fecha);
    }
}
