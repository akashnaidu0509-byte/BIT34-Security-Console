package com.bit34.security_console.controller;

import com.bit34.security_console.model.Alert;
import com.bit34.security_console.repository.AlertRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertRepository repository;

    public AlertController(AlertRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return repository.findAll();
    }

    @PostMapping
    public Alert createAlert(@RequestBody Alert alert) {
        return repository.save(alert);
    }

    @PutMapping("/{id}/status")
    public Alert updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Alert alert = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setStatus(status);

        if ("RESOLVED".equalsIgnoreCase(status)) {
            alert.setResolved(true);
        } else {
            alert.setResolved(false);
        }

        return repository.save(alert);
    }
}