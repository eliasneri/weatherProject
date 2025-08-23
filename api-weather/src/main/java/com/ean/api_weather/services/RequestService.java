package com.ean.api_weather.services;

import com.ean.api_weather.DTOS.RequestDTO;
import com.ean.api_weather.entities.RequestEntity;
import com.ean.api_weather.exceptions.BadRequestException;
import com.ean.api_weather.exceptions.ResourceNotFoundException;
import com.ean.api_weather.repositories.RequestRepository;
import com.ean.api_weather.services.helpers.FilterDataHelper;
import com.ean.api_weather.services.mappers.RequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository repository;
    private final RequestMapper mapper;

    @Transactional(readOnly = true)
    public List<RequestDTO> findAll() {
        try {
            List<RequestDTO> response = repository.findAll().stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());

            if (response.isEmpty()) {
                throw new ResourceNotFoundException("Nenhuma informação encontrada!");
            }

            return response;

        } catch (RuntimeException error) {
            throw new BadRequestException(error.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<RequestDTO> findByCity(String cidade) {
            List<RequestEntity> list = repository.findByCityIgnoreCase(cidade);

            if (list.isEmpty())
                throw new ResourceNotFoundException("Sem registros para a cidade: " + cidade + " !!!");

            return list
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RequestDTO findByCityAndToday(String cidade) {
        Optional<RequestEntity> entity = repository.findByCityAndToday(cidade);

        if (entity.isEmpty())
            throw new ResourceNotFoundException("Sem registros para a cidade: " + cidade + " com a data de hoje!!!");

        RequestEntity request = entity.get();
        FilterDataHelper.filterTodayData(request);

        return mapper.toDto(request);
    }
}
