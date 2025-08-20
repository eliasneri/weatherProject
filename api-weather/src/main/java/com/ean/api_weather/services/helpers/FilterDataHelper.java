package com.ean.api_weather.services.helpers;

import com.ean.api_weather.entities.RequestEntity;

import java.time.LocalDate;
import java.time.ZoneId;

public class FilterDataHelper {

    public static void filterTodayData(RequestEntity request) {
        if (request == null) return;

        LocalDate today = LocalDate.now();
        filterHourliesForToday(request, today);
        filterDailiesForToday(request, today);
    }

    private static void filterHourliesForToday(RequestEntity request, LocalDate today) {
        if (request.getHourlies() != null) {
            request.getHourlies().removeIf(hourly ->
                    !hourly.getTime().atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .equals(today)
            );
        }
    }

    private static void filterDailiesForToday(RequestEntity request, LocalDate today) {
        if (request.getDailies() != null) {
            request.getDailies().removeIf(daily ->
                    !daily.getSunrise().atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .equals(today)
            );
        }
    }
}
