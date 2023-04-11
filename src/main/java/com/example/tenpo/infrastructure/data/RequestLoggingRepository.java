package com.example.tenpo.infrastructure.data;

import com.example.tenpo.domain.ApiRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLoggingRepository extends JpaRepository<ApiRequest, Long> {
}
