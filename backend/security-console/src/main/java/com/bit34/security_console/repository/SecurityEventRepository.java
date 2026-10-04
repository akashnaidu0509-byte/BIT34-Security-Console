package com.bit34.security_console.repository;

import com.bit34.security_console.model.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Long> {

    boolean existsByEventKey(String eventKey);

SecurityEvent findByEventKey(String eventKey);
}