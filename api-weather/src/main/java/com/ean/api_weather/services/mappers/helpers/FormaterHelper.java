package com.ean.api_weather.services.mappers.helpers;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FormaterHelper {

    private static final ZoneId SAO_PAULO_ZONE = ZoneId.of("America/Sao_Paulo");
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    public double dividePer100(Integer temperature) {
        if (temperature > 0) {
            return ((double) temperature / 100);
        }
        return 0;
    }

    public String formaterTemperature(Integer temperature, String abbreviation) {
        if (temperature == null || (temperature < 0 && (abbreviation == null || abbreviation.isEmpty()))) {
            return "";
        }

        double newTemperature = this.dividePer100(temperature);

        return String.format("%.1f %s", newTemperature, abbreviation != null ? abbreviation : "");
    }

    public String formatInstantToLocalDateTime(Instant instant) {
        if (instant == null) return null;

        ZonedDateTime zonedDateTime = instant.atZone(SAO_PAULO_ZONE);

        return zonedDateTime.format(DATE_TIME_FORMATTER);
    }


}
