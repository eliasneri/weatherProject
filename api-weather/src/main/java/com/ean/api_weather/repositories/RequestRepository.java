package com.ean.api_weather.repositories;

import com.ean.api_weather.entities.RequestEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {

    List<RequestEntity> findByCityIgnoreCase(String city);

    @Query(value = """
    SELECT r.*, c.*, h.*, d.* 
    FROM 
        requests r
    LEFT JOIN hourlies h ON 
        r.request_id = h.hourly_fk_request_id 
        AND h.hourly_time::date = CURRENT_DATE
    LEFT JOIN dailies d ON 
        r.request_id = d.daily_fk_request_id
        AND d.daily_sunrise::date = CURRENT_DATE
        AND d.daily_sunset::date = CURRENT_DATE
    LEFT JOIN currents c ON
        r.request_id = c.current_fk_request_id
        AND c.current_day::date = CURRENT_DATE   
    WHERE 
        r.request_city ilike %:city%
    AND 
        r.request_generated::date = CURRENT_DATE
    ORDER BY 
        r.request_generated DESC
    LIMIT 1
    """, nativeQuery = true)
    Optional<RequestEntity> findByCityAndToday(@Param("city") String city);
}
