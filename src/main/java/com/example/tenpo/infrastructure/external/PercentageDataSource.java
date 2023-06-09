package com.example.tenpo.infrastructure.external;

import com.example.tenpo.infrastructure.rest.ApiDataSource;
import com.example.tenpo.services.PercentageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@EnableRetry
public class PercentageDataSource extends ApiDataSource<RemotePercentageDto, Integer>
        implements PercentageService {
    private AtomicLong lastValue = null;

    private final String percentageAPIUrl = "https://643ad15f90cd4ba563027531.mockapi.io/api/v1/percentage/%s";

    public PercentageDataSource(@Autowired RestTemplate restTemplate,
                                @Autowired RetryTemplate retryTemplate) {
        super(restTemplate, retryTemplate, RemotePercentageDto.class);
    }

    @Override
    @Cacheable(value = "percentageCache", unless = "#result == null")
    @Retryable
    public Optional<Double> getPercentage() {
        try {
            Integer rndId =  (int) (Math.random() * 100);
            RemotePercentageDto dataDto = get(rndId);
            Double percentage = dataDto.getPercentage() % 1;
            lastValue = new AtomicLong(Double.doubleToLongBits(percentage));
            return Optional.of(percentage);
        } catch (Exception e) {
            if (lastValue != null) {
                return Optional.of(Double.longBitsToDouble(lastValue.longValue()));
            }
            return Optional.empty();
        }
    }

    @Override
    protected String buildUri(Integer id) {
        return String.format(percentageAPIUrl, id);
    }
}
