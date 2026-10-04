package com.bit34.security_console.controller;

import com.bit34.security_console.model.SecurityEvent;
import com.bit34.security_console.repository.SecurityEventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
public class TimelineController {

    private final SecurityEventRepository eventRepository;

    public TimelineController(SecurityEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/api/timeline/{sourceIp}")
    public List<SecurityEvent> getTimeline(@PathVariable String sourceIp) {

        return eventRepository.findAll()
                .stream()
                .filter(event -> sourceIp.equals(event.getSourceIp()))
                .sorted(Comparator.comparing(SecurityEvent::getTimestamp))
                .toList();
    }
}