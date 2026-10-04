package com.bit34.security_console.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String severity;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String sourceIp;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private boolean resolved;
    @Column(nullable = false)
private String status = "OPEN";

    public Alert() {
    }

    public Long getId() {
        return id;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}
}