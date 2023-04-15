package com.example.tenpo.services.impl;

import com.example.tenpo.services.Calculator;
import com.example.tenpo.services.PercentageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class PercentageCalculator implements Calculator {

    private final PercentageService percentageService;

    public PercentageCalculator(PercentageService calculatorService) {
        this.percentageService = calculatorService;
    }

    @Override
    public Double calculate(Double number1, Double number2) {
        Optional<Double> percentage = percentageService.getPercentage( (int) Math.round(number1));
        if (percentage.isEmpty())
            throw new NoSuchElementException("No es posible obtener el porcentaje.");
        return (number1 + number2) * (1 * percentage.get());
    }

}
