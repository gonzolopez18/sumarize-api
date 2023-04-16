package com.example.tenpo.infrastructure.logging;
import java.io.IOException;
import java.nio.charset.StandardCharsets;;

import com.example.tenpo.domain.ApiRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Component
@WebFilter(urlPatterns = "/*")
@Order(-999)
@Slf4j
public class AccessLogFilter extends OncePerRequestFilter {
    private final RequestLoggingService loggingService;

    public AccessLogFilter(RequestLoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        ApiRequest apiRequest = getApiRequest(requestWrapper, responseWrapper);
        loggingService.save(apiRequest);

        responseWrapper.copyBodyToResponse();
    }

    private ApiRequest getApiRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) throws IOException {
        ApiRequest callToLog = new ApiRequest();
        callToLog.setResponse(getResponsePayload(response));
        callToLog.setStatusCode(response.getStatus());
        callToLog.setMethod(request.getMethod());
        callToLog.setUrl(request.getRequestURI());
        callToLog.setRequest(getRequestPayload(request));
        return callToLog;
    }

    private String getRequestPayload(ContentCachingRequestWrapper requestWrapper) {
        String payload = null;
        byte[] requestBody = requestWrapper.getContentAsByteArray();
        payload = new String(requestBody, StandardCharsets.UTF_8);
        return payload;
    }

    private String getResponsePayload(ContentCachingResponseWrapper responseWrapper) throws IOException {
        String payload = "";
        byte[] responseBody = responseWrapper.getContentAsByteArray();
        payload = new String(responseBody, StandardCharsets.UTF_8);
        return payload;
    }
}
