package com.example.tenpo.infrastructure.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ApiInterceptor implements HandlerInterceptor {

    private final RequestLoggingService loggingService;
    private final TaskExecutor taskExecutor;

    public ApiInterceptor(RequestLoggingService loggingService, TaskExecutor taskExecutor) {
        this.loggingService = loggingService;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Runnable task = () -> {
            loggingService.save(request, response);
        };
        taskExecutor.execute(task);

    }
}
