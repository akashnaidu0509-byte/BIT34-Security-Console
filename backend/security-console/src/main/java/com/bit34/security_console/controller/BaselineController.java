package com.bit34.security_console.controller;

import com.bit34.security_console.model.SecurityEvent;
import com.bit34.security_console.repository.SecurityEventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BaselineController {

    private final SecurityEventRepository eventRepository;

    public BaselineController(SecurityEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/api/baseline")
    public Map<String, Object> getBaseline() {

        List<SecurityEvent> events = eventRepository.findAll();

        Map<String, Long> failedLoginsByIp = new HashMap<>();

        for (SecurityEvent event : events) {
            if ("LOGIN_FAILED".equals(event.getEventType())) {
                failedLoginsByIp.merge(
                        event.getSourceIp(),
                        1L,
                        Long::sum
                );
            }
        }

        double average = failedLoginsByIp.values()
                .stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);

        double deviationThreshold = average * 1.5;

        List<String> flaggedIps = new ArrayList<>();

        for (Map.Entry<String, Long> entry : failedLoginsByIp.entrySet()) {
            if (entry.getValue() > deviationThreshold) {
                flaggedIps.add(entry.getKey());
            }
        }

        return Map.of(
                "failedLoginsByIp", failedLoginsByIp,
                "averageFailedLoginsPerIp", average,
                "deviationThreshold", deviationThreshold,
                "flaggedIps", flaggedIps
        );
    }
}