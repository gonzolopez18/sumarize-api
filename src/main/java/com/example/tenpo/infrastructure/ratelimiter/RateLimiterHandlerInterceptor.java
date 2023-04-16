package com.example.tenpo.infrastructure.ratelimiter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class RateLimiterHandlerInterceptor implements HandlerInterceptor {

    private final AtomicLong requestCount = new AtomicLong(0L);
    private volatile long lastRequestTimestamp = System.currentTimeMillis();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long currentCount = requestCount.incrementAndGet();
        if (currentCount > 30) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Exceeded RPM limit of 3 requests per minute");
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRequestTimestamp > 60000L) {
            requestCount.set(0L);
            lastRequestTimestamp = currentTime;
        }
    }
}
