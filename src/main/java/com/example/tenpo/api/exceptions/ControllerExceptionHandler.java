package com.example.tenpo.api.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler{

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handleException(HttpServletRequest request, Exception ex) {
        return new ApiError("internal_server_error", "An error occurred while processing your request.", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

   /* @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
        return new ApiError("route_not_found", String.format("Route %s not found.", request.getRequestURI()),
                HttpStatus.NOT_FOUND.value());
    }*/

}
