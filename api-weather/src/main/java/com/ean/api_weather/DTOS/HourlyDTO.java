package com.ean.api_weather.DTOS;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HourlyDTO {

    private Instant time;
    private String timeFormated;
    private String temperatureMax;

}
