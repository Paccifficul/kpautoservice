package ru.elizarovds.kpautoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.elizarovds.kpautoservice.model.SystemExceptionsLog;
import ru.elizarovds.kpautoservice.repository.SystemExceptionsLogRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exceptions")
@PreAuthorize("hasRole('employee')")
public class ExceptionLogController {
    private final SystemExceptionsLogRepository exceptionsLogRepository;

    @Autowired
    public ExceptionLogController(SystemExceptionsLogRepository exceptionsLogRepository) {
        this.exceptionsLogRepository = exceptionsLogRepository;
    }

    @GetMapping
    public ResponseEntity<List<SystemExceptionsLog>> getAllExceptions() {
        return ResponseEntity.ok(exceptionsLogRepository.findAll());
    }

    @GetMapping("/unhandled")
    public ResponseEntity<List<SystemExceptionsLog>> getUnhandledExceptions() {
        return ResponseEntity.ok(exceptionsLogRepository.findByHandledFalse());
    }

    @PatchMapping("/{id}/handle")
    public ResponseEntity<Void> markAsHandled(@PathVariable Long id) {
        SystemExceptionsLog log = exceptionsLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exception log not found"));
        log.setHandled(true);
        exceptionsLogRepository.save(log);
        return ResponseEntity.ok().build();
    }
}