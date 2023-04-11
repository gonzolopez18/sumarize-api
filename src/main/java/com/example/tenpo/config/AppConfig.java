package com.example.tenpo.config;

import com.example.tenpo.infrastructure.logging.ApiInterceptor;
import com.example.tenpo.infrastructure.ratelimiter.RateLimiterHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private ApiInterceptor apiInterceptor;
    @Autowired
    RateLimiterHandlerInterceptor RateLimiterHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor);
        registry.addInterceptor(RateLimiterHandlerInterceptor);
    }
}
