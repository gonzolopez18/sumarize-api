package com.example.tenpo.infrastructure.external;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class PercentageDataSourceTest {

    @Mock
    private RetryTemplate retryTemplate;

    @InjectMocks
    private PercentageDataSource percentageDataSource;

    @Test
    public void testGetPercentageWithValidId() throws Exception {
        // Arrange
        Integer validId = 9;
        RemotePercentageDto remotePercentageDto = new RemotePercentageDto();
        remotePercentageDto.setPercentage(0.5);
        String responseBody = "{\"percentage\":14918.5,\"id\":\"9\"}";
        ResponseEntity<String> response = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(retryTemplate.execute(any(), any(), any())).thenReturn(response);

        Optional<Double> percentage = percentageDataSource.getPercentage();

        assertTrue(percentage.isPresent());
        assertEquals(0.5, percentage.get(), 0.0001);
    }

    @Test
    public void testGetPercentageWithInvalidId() throws Exception {
        // Arrange
        Integer invalidId = 999;
        when(retryTemplate.execute(any(), any(), any())).thenThrow(new RestClientException("Invalid Id"));

        // Act
        Optional<Double> percentage = percentageDataSource.getPercentage();

        // Assert
        assertFalse(percentage.isPresent());
    }

}
