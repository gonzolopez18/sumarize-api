package com.example.tenpo.infrastructure.ratelimiter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RateLimiterHandlerInterceptorTests {

    @Test
    public void testPreHandleDenied() throws Exception {
        RateLimiterHandlerInterceptor interceptor = new RateLimiterHandlerInterceptor();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();
        PrintWriter writer = mock(PrintWriter.class);  // create mock PrintWriter object

        when(response.getWriter()).thenReturn(writer);

        for (int i = 1; i <= 30; i++) {
            assertTrue(interceptor.preHandle(request, response, handler));
        }

        assertFalse(interceptor.preHandle(request, response, handler));
        verify(response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
    }

    @Test
    public void testAfterCompletion() throws Exception {
        RateLimiterHandlerInterceptor interceptor = new RateLimiterHandlerInterceptor();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();
        PrintWriter writer = mock(PrintWriter.class);  // create mock PrintWriter object

        when(response.getWriter()).thenReturn(writer);

        for (int i = 1; i <= 30; i++) {
            assertTrue(interceptor.preHandle(request, response, handler));
        }

        Thread.sleep(60001L);

        interceptor.afterCompletion(request, response, handler, null);
        assertTrue(interceptor.preHandle(request, response, handler));
    }
}
