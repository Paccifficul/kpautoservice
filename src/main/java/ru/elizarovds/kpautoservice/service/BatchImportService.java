package ru.elizarovds.kpautoservice.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface BatchImportService {
    Map<String, Object> importCarsFromCsv(MultipartFile file);
    Map<String, Object> importCarsFromJson(MultipartFile file);
}