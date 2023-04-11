package com.example.tenpo.infrastructure.logging;

import com.example.tenpo.domain.ApiRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RequestLoggingService {

    void save(HttpServletRequest request, HttpServletResponse response);

    List<ApiRequest> getAll();

    Page<ApiRequest> getPaginated(PageRequest pageRequest);

}
