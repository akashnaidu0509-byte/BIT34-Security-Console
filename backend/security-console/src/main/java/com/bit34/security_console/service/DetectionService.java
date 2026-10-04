package com.bit34.security_console.service;

import com.bit34.security_console.model.Alert;
import com.bit34.security_console.model.SecurityEvent;
import com.bit34.security_console.repository.AlertRepository;
import com.bit34.security_console.repository.SecurityEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DetectionService {

    private final SecurityEventRepository eventRepository;
    private final AlertRepository alertRepository;

    public DetectionService(SecurityEventRepository eventRepository,
                            AlertRepository alertRepository) {
        this.eventRepository = eventRepository;
        this.alertRepository = alertRepository;
    }

    public void checkEvent(SecurityEvent event) {

        // Rule 1:
        // Five or more failed logins from the same IP -> HIGH alert
        if ("LOGIN_FAILED".equals(event.getEventType())) {

            List<SecurityEvent> events = eventRepository.findAll();

            long failedAttempts = events.stream()
                    .filter(e -> "LOGIN_FAILED".equals(e.getEventType()))
                    .filter(e -> e.getSourceIp().equals(event.getSourceIp()))
                    .count();

            if (failedAttempts >= 5) {

                Alert alert = new Alert();

                alert.setSeverity("HIGH");
                alert.setMessage("Multiple failed login attempts detected");
                alert.setSourceIp(event.getSourceIp());
                alert.setTimestamp(LocalDateTime.now());
                alert.setResolved(false);

                alertRepository.save(alert);
            }
        }

        // Rule 2:
        // A PORT_SCAN event -> MEDIUM alert
        if ("PORT_SCAN".equals(event.getEventType())) {

            Alert alert = new Alert();

            alert.setSeverity("MEDIUM");
            alert.setMessage("Port scanning activity detected");
            alert.setSourceIp(event.getSourceIp());
            alert.setTimestamp(LocalDateTime.now());
            alert.setResolved(false);

            alertRepository.save(alert);
        }
    }
}