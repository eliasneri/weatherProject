package com.ean.api_weather.services.mappers;
import com.ean.api_weather.DTOS.*;
import com.ean.api_weather.entities.*;
import com.ean.api_weather.services.mappers.helpers.FormaterHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestMapperTest {
    @Mock
    private FormaterHelper formaterHelper;

    @InjectMocks
    private RequestMapper mapper;

    private RequestEntity requestEntity;
    private CurrentEntity currentEntity;
    private HourlyEntity hourlyEntity;
    private DailyEntity dailyEntity;

    @BeforeEach
    void setUp() {
        requestEntity = new RequestEntity();
        requestEntity.setId(1L);
        requestEntity.setCity("São Paulo");
        requestEntity.setCityUf("SP");
        requestEntity.setLatitude(new BigDecimal("-23.5505"));
        requestEntity.setLongitude(new BigDecimal("-46.6333"));
        requestEntity.setGenerated(Instant.now());
        requestEntity.setTemperatureAbbrev("°C");

        currentEntity = new CurrentEntity();
        currentEntity.setId(1L);
        currentEntity.setDay(Instant.now());
        currentEntity.setTemperature(2500); // 25.00°C

        hourlyEntity = new HourlyEntity();
        hourlyEntity.setId(1L);
        hourlyEntity.setTime(Instant.now());
        hourlyEntity.setTemperature(2000); // 20.00°C

        dailyEntity = new DailyEntity();
        dailyEntity.setId(1L);
        dailyEntity.setSunrise(Instant.now());
        dailyEntity.setSunset(Instant.now().plusSeconds(3600 * 12));

        requestEntity.setCurrents(currentEntity);
        requestEntity.setHourlies(List.of(hourlyEntity));
        requestEntity.setDailies(List.of(dailyEntity));
    }

    @Test
    void toDto_ShouldMapRequestEntityToRequestDTO() {
        when(formaterHelper.formatInstantToLocalDateTime(any())).thenReturn("01/01/2023 12:00:00");
        when(formaterHelper.formaterTemperature(anyInt(), anyString())).thenReturn("25.0 °C");

        RequestDTO result = mapper.toDto(requestEntity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("São Paulo", result.getCity());
        assertEquals("SP", result.getCityUf());
        assertNotNull(result.getCurrents());
        assertNotNull(result.getHourlies());
        assertNotNull(result.getDailies());
        assertEquals(1, result.getHourlies().size());
        assertEquals(1, result.getDailies().size());

        verify(formaterHelper, atLeastOnce()).formatInstantToLocalDateTime(any());
        verify(formaterHelper, atLeastOnce()).formaterTemperature(anyInt(), anyString());
    }

    @Test
    void toDto_ShouldHandleNullEntity() {
        RequestDTO result = mapper.toDto(null);

        assertNull(result);
    }

    @Test
    void toCurrentDto_ShouldMapCurrentEntityToCurrentDTO() {
        when(formaterHelper.formatInstantToLocalDateTime(any())).thenReturn("01/01/2023 12:00:00");
        when(formaterHelper.formaterTemperature(anyInt(), anyString())).thenReturn("25.0 °C");

        CurrentDTO result = mapper.toCurrentDto(currentEntity, "°C");

        assertNotNull(result);
        assertEquals("25.0 °C", result.getTemperatureMax());
        verify(formaterHelper, times(1)).formatInstantToLocalDateTime(any());
        verify(formaterHelper, times(1)).formaterTemperature(anyInt(), anyString());
    }

    @Test
    void toHourlyDto_ShouldMapHourlyEntityToHourlyDTO() {
        when(formaterHelper.formatInstantToLocalDateTime(any())).thenReturn("01/01/2023 12:00:00");
        when(formaterHelper.formaterTemperature(anyInt(), anyString())).thenReturn("20.0 °C");

        HourlyDTO result = mapper.toHourlyDto(hourlyEntity, "°C");

        assertNotNull(result);
        assertEquals("20.0 °C", result.getTemperatureMax());
        verify(formaterHelper, times(1)).formatInstantToLocalDateTime(any());
        verify(formaterHelper, times(1)).formaterTemperature(anyInt(), anyString());
    }

    @Test
    void toDailyDto_ShouldMapDailyEntityToDailyDTO() {
        when(formaterHelper.formatInstantToLocalDateTime(any())).thenReturn("01/01/2023 06:00:00");

        DailyDTO result = mapper.toDailyDto(dailyEntity);

        assertNotNull(result);
        assertNotNull(result.getSunriseFormated());
        assertNotNull(result.getSunsetFormated());
        verify(formaterHelper, times(2)).formatInstantToLocalDateTime(any());
    }

}
