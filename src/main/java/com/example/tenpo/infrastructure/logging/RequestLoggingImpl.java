package com.example.tenpo.infrastructure.logging;

import com.example.tenpo.domain.ApiRequest;
import com.example.tenpo.infrastructure.data.RequestLoggingRepository;
import com.example.tenpo.services.TimeService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.time.LocalDateTime;
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
    public void save(HttpServletRequest request, HttpServletResponse response) {
        try {
            ApiRequest callToLog = new ApiRequest();
            callToLog.setMethod(request.getMethod());
            callToLog.setUrl(request.getRequestURI());
            callToLog.setRequest(getRequestPayload(request));
            callToLog.setTimestamp(timeService.getCurrentTime());
            callToLog.setStatusCode(response.getStatus());
            callToLog.setResponse("guardado");
            //callToLog.setResponse(getResponsePayload(response));
            repository.save(callToLog);
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



    private String getRequestPayload(HttpServletRequest request) {
        String payload = null;
        try {
            if (request.getContentLength() > 0) {
                payload = IOUtils.toString(request.getReader());
            }
        } catch (IOException e) {
            return "";
        }
        return payload;
    }
    private String getResponsePayload(HttpServletResponse response) {
        String payload = null;
        try {
            if (response.getBufferSize() > 0) {
                ServletOutputStream outputStream = null;
                    outputStream = response.getOutputStream();

                payload = outputStream.toString();
            }
            return payload;
        } catch (IOException e) {
            return "";
        }
    }

}
