package com.ean.api_weather.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    @JsonIgnore
    private Long id;

    @Column(name = "request_latitude")
    private BigDecimal latitude;

    @Column(name = "request_longitude")
    private BigDecimal longitude;

    @Column(name = "request_city")
    private String city;

    @Column(name = "request_city_uf")
    private String cityUf;

    @Column(name = "request_temperature_abbrev")
    private String temperatureAbbrev;

    @Column(name = "request_generated", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant generated;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<HourlyEntity> hourlies;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<DailyEntity> dailies;

    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL)
    private CurrentEntity currents;
}
