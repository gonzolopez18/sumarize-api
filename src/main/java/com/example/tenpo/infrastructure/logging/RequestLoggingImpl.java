package com.example.tenpo.infrastructure.logging;

import com.example.tenpo.domain.ApiRequest;
import com.example.tenpo.infrastructure.data.RequestLoggingRepository;
import com.example.tenpo.services.TimeService;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class RequestLoggingImpl implements RequestLoggingService {
    private final RequestLoggingRepository repository;
    private final TimeService timeService;
    public RequestLoggingImpl(RequestLoggingRepository repository, TimeService timeService) {
        this.repository = repository;
        this.timeService = timeService;
    }

    @Override
    public void save(ApiRequest request) {
        try {
            request.setTimestamp(timeService.getCurrentTime());
            repository.save(request);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    @Override
    public List<ApiRequest> getAll() {
        return repository.findAll();
    }


    public Page<ApiRequest> getPaginated(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

}
