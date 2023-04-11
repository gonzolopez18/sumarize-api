package com.example.tenpo.api.controllers;

import com.example.tenpo.domain.ApiRequest;
import com.example.tenpo.infrastructure.logging.RequestLoggingService;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tenpo/api/logging")
public class LoggerController {

    private final RequestLoggingService loggingService;

    public LoggerController(RequestLoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping(path="/getall")
    public List<ApiRequest> getAll() {
        return loggingService.getAll();
    }

    @GetMapping(path="/getpaginated")
    public Page<ApiRequest> getPaginated(int page_number, int page_size) {
        return loggingService.getPaginated(getPageable(page_number, page_size));
    }
    private PageRequest getPageable(Integer pageNumber, Integer pageSize) {
        int pageCountValue = Optional.ofNullable(pageNumber).orElse(0);
        int pageSizeValue = Optional.ofNullable(pageSize).orElse(Integer.MAX_VALUE);
        return PageRequest.of(pageCountValue, pageSizeValue);
    }

}
