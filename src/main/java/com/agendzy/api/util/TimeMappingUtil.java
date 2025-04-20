package com.agendzy.api.util;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TimeMappingUtil {

    public static DayOfWeek toDayOfWeek(String day) {
        try {
            return DayOfWeek.valueOf(day.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day of week: " + day);
        }
    }

    public static LocalTime toLocalTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid time format: " + time);
        }
    }

}
