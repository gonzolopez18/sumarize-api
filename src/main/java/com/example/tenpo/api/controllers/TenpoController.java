package com.example.tenpo.api.controllers;

import com.example.tenpo.services.Calculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tenpo/api")
@RestController
public class TenpoController {

    private final Calculator calculator;

    public TenpoController(Calculator calculator) {
        this.calculator = calculator;
    }

    @GetMapping(path="/summarize/{number1}/{number2}")
    public Double getSum(@PathVariable("number1") Double sumando1, @PathVariable("number2") Double sumando2) {
        return calculator.calculate(sumando1, sumando2);
    }
}
