package com.example.tenpo.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Slf4j
@EnableRetry
public abstract class ApiDataSource<T, U> {
   private final RestTemplate restTemplate;
   private final RetryTemplate retryTemplate;
    protected final Class<T> typeReference;
    protected ApiDataSource(RestTemplate restTemplate,
                            @Autowired RetryTemplate retryTemplate, Class<T> typeReference) {
        this.restTemplate = restTemplate;
        this.retryTemplate = retryTemplate;
        this.typeReference = typeReference;
    }

    public T get(U id) {
        ResponseEntity<String> response = retryTemplate.execute(retryContext -> {
            log.info("Trying " + id.toString());
            return restTemplate.exchange(buildUri(id), HttpMethod.GET, null, String.class);
        });
        return getObjectFromResponse(response);
    }


    protected abstract String buildUri(U id);

    protected T getObjectFromResponse(ResponseEntity<String> response) {
            try {
                if (isSuccess(response.getStatusCode())) {
                    ObjectMapper mapper = new ObjectMapper();
                    T dto = mapper.readValue(response.getBody(), this.typeReference);
                    return dto;
                }
                throw new Exception("Error al consultar API externa.");
            } catch (Exception e ) {
                throw new ServiceException("Error en api externa.", e);
            }

    }

    private boolean isSuccess(HttpStatusCode statusCode) {
        return statusCode.toString().charAt(0) == "2".charAt(0);
    }
}
