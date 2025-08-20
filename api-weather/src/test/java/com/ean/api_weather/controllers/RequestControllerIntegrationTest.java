package com.ean.api_weather.controllers;
import com.ean.api_weather.DTOS.RequestDTO;
import com.ean.api_weather.exceptions.ResourceNotFoundException;
import com.ean.api_weather.services.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RequestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestService requestService;

    @Test
    void getAllRequests_ShouldReturnListOfRequests() throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(1L);
        requestDTO.setCity("São Paulo");
        requestDTO.setCityUf("SP");
        requestDTO.setLatitude(new BigDecimal("-23.5505"));
        requestDTO.setLongitude(new BigDecimal("-46.6333"));
        requestDTO.setGenerated(Instant.now());

        when(requestService.findAll()).thenReturn(List.of(requestDTO));

        // Act & Assert
        mockMvc.perform(get("/api/v1/clima")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].city", is("São Paulo")));
    }

    @Test
    void getAllRequests_ShouldReturnNotFoundWhenNoData() throws Exception {
        when(requestService.findAll()).thenThrow(new ResourceNotFoundException("Nenhuma informação encontrada!"));

        mockMvc.perform(get("/api/v1/clima")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Nenhuma informação encontrada!")));
    }

    @Test
    void getRequestsByCity_ShouldReturnRequestsForCity() throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(1L);
        requestDTO.setCity("São Paulo");
        requestDTO.setCityUf("SP");

        when(requestService.findByCity("São Paulo")).thenReturn(List.of(requestDTO));

        mockMvc.perform(get("/api/v1/clima/São Paulo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].city", is("São Paulo")));
    }

    @Test
    void getRequestsByCity_ShouldReturnNotFoundWhenCityNotFound() throws Exception {
        when(requestService.findByCity("Unknown City"))
                .thenThrow(new ResourceNotFoundException("Sem registros para a cidade: Unknown City !!!"));

        mockMvc.perform(get("/api/v1/clima/Unknown City")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Unknown City")));
    }

    @Test
    void getTodayRequestByCity_ShouldReturnTodayRequest() throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(1L);
        requestDTO.setCity("São Paulo");
        requestDTO.setCityUf("SP");

        when(requestService.findByCityAndToday("São Paulo")).thenReturn(requestDTO);

        mockMvc.perform(get("/api/v1/clima/São Paulo/hoje")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city", is("São Paulo")));
    }

    @Test
    void getTodayRequestByCity_ShouldReturnNotFoundWhenNoTodayData() throws Exception {
        // Arrange
        when(requestService.findByCityAndToday("São Paulo"))
                .thenThrow(new ResourceNotFoundException("Sem registros para a cidade: São Paulo com a data de hoje!!!"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/clima/São Paulo/hoje")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("São Paulo")));
    }
}
