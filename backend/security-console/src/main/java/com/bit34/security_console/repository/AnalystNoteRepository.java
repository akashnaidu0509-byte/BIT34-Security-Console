package com.bit34.security_console.repository;

import com.bit34.security_console.model.AnalystNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalystNoteRepository extends JpaRepository<AnalystNote, Long> {

    List<AnalystNote> findByAlertId(Long alertId);
}