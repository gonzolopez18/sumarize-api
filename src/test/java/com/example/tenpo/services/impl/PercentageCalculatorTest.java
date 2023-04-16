package com.example.tenpo.services.impl;

import com.example.tenpo.services.PercentageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PercentageCalculatorTest {

    @Mock
    private PercentageService percentageService;

    @InjectMocks
    private PercentageCalculator calculator;

    @ParameterizedTest
    @MethodSource("getCalculatorData")
    public void calculateTest(double number1, double number2, Double percentage, double expected) {
        when(percentageService.getPercentage()).thenReturn(Optional.of(percentage));

        double result = calculator.calculate(number1, number2);

        assertEquals(expected, result);

    }

    private static Stream<Arguments> getCalculatorData() {
        return Stream.of(
                Arguments.of(1, 1, 0.5, 1),
                Arguments.of(0,0,1.0,0)
        );
    }

    @Test
    public void calculateThrowsError() {
        double number1 = 5;
        double number2 = 5;
        when(percentageService.getPercentage()).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> calculator.calculate(number1, number2));
    }
}
