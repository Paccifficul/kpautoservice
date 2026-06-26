package ru.elizarovds.kpautoservice.service;

import java.util.Map;

public interface ReportService {
    Map<String, Object> getCarsReport();
    Map<String, Object> getServicePopularityReport();
    Map<String, Object> getServiceTimeReport();
}