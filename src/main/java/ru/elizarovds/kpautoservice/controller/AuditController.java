package ru.elizarovds.kpautoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.elizarovds.kpautoservice.model.AuditLog;
import ru.elizarovds.kpautoservice.repository.AuditLogRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
@PreAuthorize("hasRole('employee')")
public class AuditController {
    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        return ResponseEntity.ok(auditLogRepository.findAll());
    }

    @GetMapping("/table/{tableName}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByTable(@PathVariable String tableName) {
        return ResponseEntity.ok(auditLogRepository.findByTableName(tableName));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUser(@PathVariable String email) {
        return ResponseEntity.ok(auditLogRepository.findByUserEmail(email));
    }

    @GetMapping("/period")
    public ResponseEntity<List<AuditLog>> getAuditLogsByPeriod(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(auditLogRepository.findByTimestampBetween(start, end));
    }
}