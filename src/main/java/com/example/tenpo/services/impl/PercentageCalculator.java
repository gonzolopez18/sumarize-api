package com.example.tenpo.services.impl;

import com.example.tenpo.services.Calculator;
import com.example.tenpo.services.PercentageService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        Double percentage = Optional.ofNullable(percentageService.getPercentage()).orElseThrow();
        log.info(percentage.toString());
        return (number1 + number2) * (1 * percentage);
    }
}
