package com.ean.api_weather.controllers;

import com.ean.api_weather.DTOS.RequestDTO;
import com.ean.api_weather.services.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "ean - Api Weather")
public class RequestController {

    private final RequestService service;
    private final RestTemplate restTemplate;

    @Operation(summary = "Busca todos os resultados no banco de Dados!", method = "GET")
    @GetMapping("/clima")
    public ResponseEntity<List<RequestDTO>> findAll(){
        List<RequestDTO> response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Busca todos os resultados no banco de Dados pela Cidade informada!", method = "GET")
    @GetMapping("/clima/{cidade}")
    public ResponseEntity<List<RequestDTO>> findByCity(@Valid @PathVariable String cidade) {
        List<RequestDTO> response = service.findByCity(cidade);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Busca Resultado mais recente para a Cidade informada!", method = "GET")
    @GetMapping("/clima/{cidade}/hoje")
    public ResponseEntity<RequestDTO> findByCityAndToday(@Valid @PathVariable String cidade) {
        RequestDTO response = service.findByCityAndToday(cidade);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Expor métricas Prometheus (proxy para /actuator/prometheus)", method = "GET")
    @GetMapping(value = "/metrics", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> prometheusMetrics() {
        String prometheusEndpoint = "http://localhost:9090/actuator/prometheus";
        String metrics = restTemplate.getForObject(prometheusEndpoint, String.class);
        return ResponseEntity.ok(metrics);
    }

}
