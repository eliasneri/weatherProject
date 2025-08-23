package com.ean.api_weather.DTOS;


import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    private Long id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String city;
    private String cityUf;
    private Instant generated;
    private String generatedFormated;
    private CurrentDTO currents;
    private List<DailyDTO> dailies;
    private List<HourlyDTO> hourlies;
}
