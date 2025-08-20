package com.ean.api_weather.services.mappers;

import com.ean.api_weather.DTOS.CurrentDTO;
import com.ean.api_weather.DTOS.DailyDTO;
import com.ean.api_weather.DTOS.HourlyDTO;
import com.ean.api_weather.DTOS.RequestDTO;
import com.ean.api_weather.entities.CurrentEntity;
import com.ean.api_weather.entities.DailyEntity;
import com.ean.api_weather.entities.HourlyEntity;
import com.ean.api_weather.entities.RequestEntity;
import com.ean.api_weather.services.mappers.helpers.FormaterHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    private final FormaterHelper formaterHelper;

    public RequestDTO toDto(RequestEntity entity) {
        if (entity == null) {
            return null;
        }

        RequestDTO dto = new RequestDTO();

        dto.setId(entity.getId());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setCity(entity.getCity());
        dto.setCityUf(entity.getCityUf());
        dto.setGenerated(entity.getGenerated());
        dto.setGeneratedFormated(formaterHelper.formatInstantToLocalDateTime(entity.getGenerated()));

        if (entity.getHourlies() != null) {
            List<HourlyDTO> hourlyDTOList = new ArrayList<>();

            for (HourlyEntity hourlyEntity : entity.getHourlies()) {
                hourlyDTOList.add(toHourlyDto(hourlyEntity, entity.getTemperatureAbbrev()));
            }
            dto.setHourlies(hourlyDTOList);
        }

        if (entity.getDailies() != null) {
            List<DailyDTO> dailyDTOList = new ArrayList<>();

            for (DailyEntity dailyEntity : entity.getDailies()) {
                dailyDTOList.add(toDailyDto(dailyEntity));
            }

            dto.setDailies(dailyDTOList);
        }

        if (entity.getCurrents() != null) {
            dto.setCurrents(toCurrentDto(entity.getCurrents(), entity.getTemperatureAbbrev()));
        }

        return dto;
    }

    public HourlyDTO toHourlyDto(HourlyEntity entity, String abbreviation) {
        if (entity == null) {
            return null;
        }

        HourlyDTO dto = new HourlyDTO();
        dto.setTime(entity.getTime());
        dto.setTimeFormated(formaterHelper.formatInstantToLocalDateTime(entity.getTime()));
        dto.setTemperatureMax(formaterHelper.formaterTemperature(entity.getTemperature(), abbreviation));

        return dto;
    }

    public DailyDTO toDailyDto(DailyEntity entity) {
        if (entity == null) {
            return null;
        }

        DailyDTO dto = new DailyDTO();
        dto.setSunrise(entity.getSunrise());
        dto.setSunriseFormated(formaterHelper.formatInstantToLocalDateTime(entity.getSunrise()));
        dto.setSunset(entity.getSunset());
        dto.setSunsetFormated(formaterHelper.formatInstantToLocalDateTime(entity.getSunset()));


        return dto;
    }

    public CurrentDTO toCurrentDto(CurrentEntity entity, String abbreviation) {
        if (entity == null) {
            return null;
        }

        CurrentDTO dto = new CurrentDTO();
        dto.setDay(entity.getDay());
        dto.setDayFormated(formaterHelper.formatInstantToLocalDateTime(entity.getDay()));
        dto.setTemperatureMax(formaterHelper.formaterTemperature(entity.getTemperature(), abbreviation));

        return dto;
    }
}
