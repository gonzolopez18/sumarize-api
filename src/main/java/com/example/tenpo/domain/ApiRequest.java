package com.example.tenpo.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ApiRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    private String url;

    private LocalDateTime timestamp;

    @Column(length = 100)
    private String request;

    @Column(length = 100)
    private String response;

    private int statusCode;
}
