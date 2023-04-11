package com.example.tenpo.services.impl;

import com.example.tenpo.services.PercentageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RemotePercentageImpl implements PercentageService {
    @Override
    public Double getPercentage() {
        Double percentage = Math.random();
        //if ( percentage < 0.9 ) throw new RuntimeException("Error al intentar obtener el porcentagje");
        return percentage;
    }
}
