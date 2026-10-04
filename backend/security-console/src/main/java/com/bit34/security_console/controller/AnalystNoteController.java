package com.bit34.security_console.controller;

import com.bit34.security_console.model.AnalystNote;
import com.bit34.security_console.repository.AnalystNoteRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class AnalystNoteController {

    private final AnalystNoteRepository repository;

    public AnalystNoteController(AnalystNoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/alert/{alertId}")
    public List<AnalystNote> getNotes(@PathVariable Long alertId) {
        return repository.findByAlertId(alertId);
    }

    @PostMapping
    public AnalystNote createNote(@RequestBody AnalystNote note) {
        note.setTimestamp(LocalDateTime.now());
        return repository.save(note);
    }
}