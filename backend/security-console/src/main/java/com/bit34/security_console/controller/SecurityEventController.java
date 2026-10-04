
package com.bit34.security_console.controller;

import com.bit34.security_console.model.SecurityEvent;
import com.bit34.security_console.repository.SecurityEventRepository;
import com.bit34.security_console.service.DetectionService;
import com.bit34.security_console.service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class SecurityEventController {

    private final SecurityEventRepository repository;
    private final DetectionService detectionService;
    private final KafkaProducerService kafkaProducerService;

    public SecurityEventController(
            SecurityEventRepository repository,
            DetectionService detectionService,
            KafkaProducerService kafkaProducerService) {
        this.repository = repository;
        this.detectionService = detectionService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping
    public List<SecurityEvent> getAllEvents() {
        return repository.findAll();
    }

    @PostMapping
    public SecurityEvent createEvent(@RequestBody SecurityEvent event) {

        if (event.getEventKey() == null || event.getEventKey().isBlank()) {
    event.setEventKey(UUID.randomUUID().toString());
}

if (repository.existsByEventKey(event.getEventKey())) {
    return repository.findByEventKey(event.getEventKey());
}

        SecurityEvent savedEvent = repository.save(event);

        detectionService.checkEvent(savedEvent);

        String message = String.format(
                "{\"id\":%d,\"eventType\":\"%s\",\"sourceIp\":\"%s\",\"username\":\"%s\"}",
                savedEvent.getId(),
                savedEvent.getEventType(),
                savedEvent.getSourceIp(),
                savedEvent.getUsername()
        );

        kafkaProducerService.sendEvent(message);

        return savedEvent;
    }
}