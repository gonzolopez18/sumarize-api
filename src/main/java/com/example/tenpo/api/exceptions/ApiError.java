package com.example.tenpo.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {

    private String error;
    private String message;
    private Integer status;

}
