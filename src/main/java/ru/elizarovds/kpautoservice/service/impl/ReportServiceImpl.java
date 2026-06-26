package ru.elizarovds.kpautoservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.elizarovds.kpautoservice.repository.CarRepository;
import ru.elizarovds.kpautoservice.repository.ServiceStatsViewRepository;
import ru.elizarovds.kpautoservice.repository.CarOrdersViewRepository;
import ru.elizarovds.kpautoservice.service.ReportService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private final CarRepository carRepository;
    private final ServiceStatsViewRepository serviceStatsViewRepository;
    private final CarOrdersViewRepository carOrdersViewRepository;

    @Autowired
    public ReportServiceImpl(CarRepository carRepository, ServiceStatsViewRepository serviceStatsViewRepository, CarOrdersViewRepository carOrdersViewRepository) {
        this.carRepository = carRepository;
        this.serviceStatsViewRepository = serviceStatsViewRepository;
        this.carOrdersViewRepository = carOrdersViewRepository;
    }

    @Override
    public Map<String, Object> getCarsReport() {
        Map<String, Object> report = new HashMap<>();

        report.put("totalCars", carRepository.count());
        report.put("carsByBrand", carRepository.countByBrand().stream()
                .collect(Collectors.toMap(
                        CarRepository.GroupCount::getName,
                        CarRepository.GroupCount::getCount
                )));
        report.put("carsByYear", carRepository.countByYear().stream()
                .collect(Collectors.toMap(
                        CarRepository.GroupCount::getName,
                        CarRepository.GroupCount::getCount
                )));
        
        return report;
    }

    @Override
    public Map<String, Object> getServicePopularityReport() {
        Map<String, Object> report = new HashMap<>();
        
        report.put("popularServices", serviceStatsViewRepository.findMostPopularServices());
        report.put("profitableServices", serviceStatsViewRepository.findMostProfitableServices());
        
        return report;
    }

    @Override
    public Map<String, Object> getServiceTimeReport() {
        Map<String, Object> report = new HashMap<>();
        
        report.put("serviceStats", serviceStatsViewRepository.findAll());
        report.put("ordersSummary", carOrdersViewRepository.countByOrderStatus().stream()
                .collect(Collectors.toMap(
                        CarOrdersViewRepository.StatusCount::getStatus,
                        CarOrdersViewRepository.StatusCount::getCount
                )));
        
        return report;
    }
}
