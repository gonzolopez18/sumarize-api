package com.example.tenpo.infrastructure.time;

import com.example.tenpo.services.TimeService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class TimeServiceImpl implements TimeService {

    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
