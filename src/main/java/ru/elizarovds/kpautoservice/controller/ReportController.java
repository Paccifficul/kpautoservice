package ru.elizarovds.kpautoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.elizarovds.kpautoservice.service.ReportService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reports")
@PreAuthorize("hasRole('employee')")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/cars")
    public ResponseEntity<Map<String, Object>> getCarsReport() {
        return ResponseEntity.ok(reportService.getCarsReport());
    }

    @GetMapping("/services/popularity")
    public ResponseEntity<Map<String, Object>> getServicePopularityReport() {
        return ResponseEntity.ok(reportService.getServicePopularityReport());
    }

    @GetMapping("/services/time")
    public ResponseEntity<Map<String, Object>> getServiceTimeReport() {
        return ResponseEntity.ok(reportService.getServiceTimeReport());
    }
}