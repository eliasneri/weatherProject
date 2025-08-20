package com.ean.api_weather.services;

import com.ean.api_weather.DTOS.RequestDTO;
import com.ean.api_weather.entities.RequestEntity;
import com.ean.api_weather.exceptions.ResourceNotFoundException;
import com.ean.api_weather.repositories.RequestRepository;
import com.ean.api_weather.services.mappers.RequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
    @Mock
    private RequestRepository repository;

    @Mock
    private RequestMapper mapper;

    @InjectMocks
    private RequestService service;

    private RequestEntity requestEntity;
    private RequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestEntity = new RequestEntity();
        requestEntity.setId(1L);
        requestEntity.setCity("São Paulo");
        requestEntity.setCityUf("SP");
        requestEntity.setLatitude(new BigDecimal("-23.5505"));
        requestEntity.setLongitude(new BigDecimal("-46.6333"));
        requestEntity.setGenerated(Instant.now());

        requestDTO = new RequestDTO();
        requestDTO.setId(1L);
        requestDTO.setCity("São Paulo");
        requestDTO.setCityUf("SP");
    }

    @Test
    void findAll_ShouldReturnListOfRequests() {
        when(repository.findAll()).thenReturn(List.of(requestEntity));
        when(mapper.toDto(requestEntity)).thenReturn(requestDTO);

        List<RequestDTO> result = service.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("São Paulo", result.get(0).getCity());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDto(requestEntity);
    }

    @Test
    void findByCity_ShouldReturnRequestsForCity() {
        // Arrange
        String city = "São Paulo";
        when(repository.findByCityIgnoreCase(city)).thenReturn(List.of(requestEntity));
        when(mapper.toDto(requestEntity)).thenReturn(requestDTO);

        List<RequestDTO> result = service.findByCity(city);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("São Paulo", result.get(0).getCity());
        verify(repository, times(1)).findByCityIgnoreCase(city);
        verify(mapper, times(1)).toDto(requestEntity);
    }

    @Test
    void findByCity_ShouldThrowResourceNotFoundExceptionWhenCityNotFound() {
        String city = "Unknown City";
        when(repository.findByCityIgnoreCase(city)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> service.findByCity(city));
    }

    @Test
    void findByCityAndToday_ShouldReturnTodayRequestForCity() {
        String city = "São Paulo";
        when(repository.findByCityAndToday(city)).thenReturn(Optional.of(requestEntity));
        when(mapper.toDto(requestEntity)).thenReturn(requestDTO);

        RequestDTO result = service.findByCityAndToday(city);

        assertNotNull(result);
        assertEquals("São Paulo", result.getCity());
        verify(repository, times(1)).findByCityAndToday(city);
        verify(mapper, times(1)).toDto(requestEntity);
    }

    @Test
    void findByCityAndToday_ShouldThrowResourceNotFoundExceptionWhenNoTodayData() {
        String city = "São Paulo";
        when(repository.findByCityAndToday(city)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findByCityAndToday(city));
    }
}
