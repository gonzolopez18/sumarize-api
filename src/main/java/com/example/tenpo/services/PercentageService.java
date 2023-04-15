package com.example.tenpo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;

import java.util.Optional;

public interface PercentageService {

    public Optional<Double> getPercentage(Integer id);
}
