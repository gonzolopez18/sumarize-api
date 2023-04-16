package com.example.tenpo.infrastructure.logging;

import com.example.tenpo.domain.ApiRequest;
import com.example.tenpo.infrastructure.data.RequestLoggingRepository;
import com.example.tenpo.services.TimeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.bytebuddy.asm.Advice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestLoggingImplTest {

    private RequestLoggingService requestLoggingService;

    @Mock
    private RequestLoggingRepository repositoryMock;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private TimeService timeService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        requestLoggingService = new RequestLoggingImpl(repositoryMock, timeService);
    }

    @Test
    public void testSave() throws IOException {
        String payload = "test payload";
        when(requestMock.getMethod()).thenReturn("POST");
        when(requestMock.getRequestURI()).thenReturn("/test");
        BufferedReader reader = new BufferedReader(new StringReader(payload));
        when(requestMock.getContentLength()).thenReturn(1);
        when(requestMock.getReader()).thenReturn(reader);
        when(responseMock.getStatus()).thenReturn(200);
        LocalDateTime expectedDateTime = LocalDateTime.now();
        when(timeService.getCurrentTime()).thenReturn(expectedDateTime);

        // Call the method under test
        requestLoggingService.save(requestMock, responseMock);

        // Verify that the repository.save() method was called once with the expected arguments
        ApiRequest expectedRequest = new ApiRequest();
        expectedRequest.setMethod("POST");
        expectedRequest.setUrl("/test");
        expectedRequest.setRequest(payload);
        expectedRequest.setTimestamp(expectedDateTime);
        expectedRequest.setStatusCode(200);
        expectedRequest.setResponse("guardado");
        verify(repositoryMock, times(1)).save(expectedRequest);
    }

    @Test
    public void testGetAll() {
        // Call the method under test
        List<ApiRequest> expectedRequests = List.of(new ApiRequest(), new ApiRequest());
        when(repositoryMock.findAll()).thenReturn(expectedRequests);
        List<ApiRequest> actualRequests = requestLoggingService.getAll();

        // Verify that the expected requests were returned
        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    public void testGetPaginated() {
        // Call the method under test
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ApiRequest> expectedPage = mock(Page.class);
        when(repositoryMock.findAll(pageRequest)).thenReturn(expectedPage);
        Page<ApiRequest> actualPage = requestLoggingService.getPaginated(pageRequest);

        // Verify that the expected page was returned
        assertEquals(expectedPage, actualPage);
    }
}
