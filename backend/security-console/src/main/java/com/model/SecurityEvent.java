package com.bit34.security_console.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "security_events")
public class SecurityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
private String eventKey;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String sourceIp;

    private String username;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String details;

    public SecurityEvent() {
    }

    public Long getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
 
   }

   public String getEventKey() {
    return eventKey;
}

public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
}
}