package ru.elizarovds.kpautoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.elizarovds.kpautoservice.service.BatchImportService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/batch")
@PreAuthorize("hasRole('employee')")
public class BatchImportController {
    private final BatchImportService batchImportService;

    @Autowired
    public BatchImportController(BatchImportService batchImportService) {
        this.batchImportService = batchImportService;
    }

    @PostMapping("/cars/csv")
    public ResponseEntity<Map<String, Object>> importCarsFromCsv(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(batchImportService.importCarsFromCsv(file));
    }

    @PostMapping("/cars/json")
    public ResponseEntity<Map<String, Object>> importCarsFromJson(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(batchImportService.importCarsFromJson(file));
    }
}