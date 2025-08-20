package com.ean.api_weather.DTOS;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentDTO {

    private Instant day;
    private String dayFormated;
    private String temperatureMax;

}
