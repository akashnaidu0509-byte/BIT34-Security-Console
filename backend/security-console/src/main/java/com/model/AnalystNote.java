package com.bit34.security_console.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analyst_notes")
public class AnalystNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long alertId;

    @Column(nullable = false)
    private String analyst;

    @Column(nullable = false, length = 1000)
    private String note;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public AnalystNote() {
    }

    public Long getId() {
        return id;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public String getAnalyst() {
        return analyst;
    }

    public void setAnalyst(String analyst) {
        this.analyst = analyst;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}