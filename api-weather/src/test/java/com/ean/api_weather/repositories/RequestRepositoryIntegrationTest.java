package com.ean.api_weather.repositories;

import com.ean.api_weather.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class RequestRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RequestRepository requestRepository;

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void findByCityIgnoreCase_ShouldReturnRequestsForCity() {
        RequestEntity request = new RequestEntity();
        request.setCity("São Paulo");
        request.setCityUf("SP");
        request.setLatitude(new BigDecimal("-23.5505"));
        request.setLongitude(new BigDecimal("-46.6333"));
        request.setGenerated(Instant.now());
        entityManager.persist(request);
        entityManager.flush();
        entityManager.clear();

        List<RequestEntity> found = requestRepository.findByCityIgnoreCase("são paulo");

        assertFalse(found.isEmpty());
        assertEquals("São Paulo", found.get(0).getCity());
    }

    @Test
    void findByCityIgnoreCase_ShouldReturnEmptyListWhenCityNotFound() {
        List<RequestEntity> found = requestRepository.findByCityIgnoreCase("Unknown City");
        assertTrue(found.isEmpty());
    }

    @Test
    void findByCityAndToday_ShouldReturnTodayRequestForCity() {
        RequestEntity request = new RequestEntity();
        request.setCity("São Paulo");
        request.setCityUf("SP");
        request.setLatitude(new BigDecimal("-23.5505"));
        request.setLongitude(new BigDecimal("-46.6333"));
        request.setGenerated(Instant.now());
        entityManager.persist(request);

        CurrentEntity current = new CurrentEntity();
        current.setRequest(request);
        current.setDay(Instant.now());
        current.setTemperature(2500);
        entityManager.persist(current);

        HourlyEntity hourly = new HourlyEntity();
        hourly.setRequest(request);
        hourly.setTime(Instant.now());
        hourly.setTemperature(2000);
        entityManager.persist(hourly);

        DailyEntity daily = new DailyEntity();
        daily.setRequest(request);
        daily.setSunrise(Instant.now());
        daily.setSunset(Instant.now().plusSeconds(3600 * 12));
        entityManager.persist(daily);

        entityManager.flush();
        entityManager.clear();

        Optional<RequestEntity> found = requestRepository.findByCityAndToday("São Paulo");

        assertTrue(found.isPresent());
        assertEquals("São Paulo", found.get().getCity());
        assertNotNull(found.get().getCurrents());
        assertFalse(found.get().getHourlies().isEmpty());
        assertFalse(found.get().getDailies().isEmpty());
    }

    @Test
    void findByCityAndToday_ShouldReturnEmptyWhenNoTodayData() {
        RequestEntity request = new RequestEntity();
        request.setCity("São Paulo");
        request.setCityUf("SP");
        request.setLatitude(new BigDecimal("-23.5505"));
        request.setLongitude(new BigDecimal("-46.6333"));
        request.setGenerated(Instant.now().minusSeconds(3600 * 24)); // ontem
        entityManager.persist(request);
        entityManager.flush();
        entityManager.clear();

        Optional<RequestEntity> found = requestRepository.findByCityAndToday("São Paulo");

        assertTrue(found.isEmpty());
    }
}
