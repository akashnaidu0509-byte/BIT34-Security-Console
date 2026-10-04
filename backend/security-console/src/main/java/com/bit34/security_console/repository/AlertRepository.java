package com.bit34.security_console.repository;

import com.bit34.security_console.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}