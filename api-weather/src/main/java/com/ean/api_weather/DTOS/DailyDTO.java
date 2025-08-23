package com.ean.api_weather.DTOS;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyDTO {

    private Instant sunrise;
    private String sunriseFormated;
    private Instant sunset;
    private String sunsetFormated;
}
