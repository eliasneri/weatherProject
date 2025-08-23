package com.ean.api_weather.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "dailies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_fk_request_id")
    @JsonIgnore
    private RequestEntity request;

    @Column(name = "daily_sunrise", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant sunrise;

    @Column(name = "daily_sunset", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant sunset;
}
