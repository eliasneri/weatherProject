package com.ean.api_weather.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "hourlies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourlyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hourly_id")
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hourly_fk_request_id", nullable = false)
    @JsonIgnore
    private RequestEntity request;

    @Column(name = "hourly_time", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant time;

    @Column(name = "hourly_temperature", nullable = false)
    private Integer temperature;
}
