package ru.elizarovds.kpautoservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.elizarovds.kpautoservice.model.Car;
import ru.elizarovds.kpautoservice.model.SystemExceptionsLog;
import ru.elizarovds.kpautoservice.repository.SystemExceptionsLogRepository;
import ru.elizarovds.kpautoservice.service.BatchImportService;
import ru.elizarovds.kpautoservice.service.CarService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

@Service
public class BatchImportServiceImpl implements BatchImportService {
    private final CarService carService;
    private final SystemExceptionsLogRepository exceptionsLogRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public BatchImportServiceImpl(CarService carService, SystemExceptionsLogRepository exceptionsLogRepository) {
        this.carService = carService;
        this.exceptionsLogRepository = exceptionsLogRepository;
    }

    @Override
    public Map<String, Object> importCarsFromCsv(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        int successCount = 0;
        int errorCount = 0;
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine(); // Skip header
            
            while ((line = reader.readLine()) != null) {
                try {
                    Car car = parseCsvLine(line);
                    carService.saveCar(car);
                    successCount++;
                } catch (Exception e) {
                    errorCount++;
                    logException("batch_import", "import_cars_csv", "CSV_PARSE_ERROR", 
                               e.getMessage(), line);
                }
            }
        } catch (Exception e) {
            logException("batch_import", "import_cars_csv", "FILE_READ_ERROR", 
                       e.getMessage(), null);
        }
        
        result.put("successCount", successCount);
        result.put("errorCount", errorCount);
        return result;
    }

    @Override
    public Map<String, Object> importCarsFromJson(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        int successCount = 0;
        int errorCount = 0;
        
        try {
            Car[] cars = objectMapper.readValue(file.getInputStream(), Car[].class);
            
            for (Car car : cars) {
                try {
                    carService.saveCar(car);
                    successCount++;
                } catch (Exception e) {
                    errorCount++;
                    logException("batch_import", "import_cars_json", "JSON_SAVE_ERROR", 
                               e.getMessage(), car.toString());
                }
            }
        } catch (Exception e) {
            logException("batch_import", "import_cars_json", "JSON_PARSE_ERROR", 
                       e.getMessage(), null);
        }
        
        result.put("successCount", successCount);
        result.put("errorCount", errorCount);
        return result;
    }

    private Car parseCsvLine(String line) {
        String[] fields = line.split(",");
        Car car = new Car();
        car.setVin(fields[0]);
        car.setBrand(fields[1]);
        car.setModel(fields[2]);
        car.setYear(Short.parseShort(fields[3]));
        car.setEngineType(Car.EngineType.valueOf(fields[4]));
        car.setEngineVolume(new BigDecimal(fields[5]));
        car.setEnginePowerHp(Short.parseShort(fields[6]));
        car.setTransmissionType(Car.TransmissionType.valueOf(fields[7]));
        car.setDriveType(Car.DriveType.valueOf(fields[8]));
        car.setFuelType(Car.FuelType.valueOf(fields[9]));
        car.setBodyType(fields[10]);
        car.setMileage(Long.parseLong(fields[11]));
        car.setColor(fields[12]);
        return car;
    }

    private void logException(String source, String operation, String errorCode, 
                            String errorMessage, String payload) {
        SystemExceptionsLog log = new SystemExceptionsLog();
        log.setSource(source);
        log.setOperation(operation);
        log.setErrorCode(errorCode);
        log.setErrorMessage(errorMessage);
        log.setPayload(payload);
        exceptionsLogRepository.save(log);
    }
}