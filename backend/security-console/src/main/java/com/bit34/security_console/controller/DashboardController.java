package com.bit34.security_console.controller;

import com.bit34.security_console.repository.AlertRepository;
import com.bit34.security_console.repository.SecurityEventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardController {

    private final SecurityEventRepository eventRepository;
    private final AlertRepository alertRepository;

    public DashboardController(SecurityEventRepository eventRepository,
                               AlertRepository alertRepository) {
        this.eventRepository = eventRepository;
        this.alertRepository = alertRepository;
    }

    @GetMapping("/api/dashboard")
    public Map<String, Long> dashboard() {
        return Map.of(
                "totalEvents", eventRepository.count(),
                "totalAlerts", alertRepository.count()
        );
    }
}