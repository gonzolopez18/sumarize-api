package com.example.tenpo.services;

import org.springframework.cache.annotation.Cacheable;

public interface PercentageService {

    @Cacheable(value = "percentageCache")
    public Double getPercentage() ;
}
