package ru.elizarovds.kpautoservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.elizarovds.kpautoservice.model.Car;
import ru.elizarovds.kpautoservice.model.CarBrand;
import ru.elizarovds.kpautoservice.model.CarModel;
import ru.elizarovds.kpautoservice.model.CarSpecification;
import ru.elizarovds.kpautoservice.repository.CarBrandRepository;
import ru.elizarovds.kpautoservice.repository.CarModelRepository;
import ru.elizarovds.kpautoservice.repository.CarRepository;
import ru.elizarovds.kpautoservice.repository.CarSpecificationRepository;
import ru.elizarovds.kpautoservice.service.CarService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarBrandRepository carBrandRepository;
    private final CarModelRepository carModelRepository;
    private final CarSpecificationRepository carSpecificationRepository;
    private final AuditContextService auditContextService;

    @Autowired
    public CarServiceImpl(
            CarRepository carRepository,
            CarBrandRepository carBrandRepository,
            CarModelRepository carModelRepository,
            CarSpecificationRepository carSpecificationRepository,
            AuditContextService auditContextService
    ) {
        this.carRepository = carRepository;
        this.carBrandRepository = carBrandRepository;
        this.carModelRepository = carModelRepository;
        this.carSpecificationRepository = carSpecificationRepository;
        this.auditContextService = auditContextService;
    }

    @Override
    public Car saveCar(Car car) {
        auditContextService.setCurrentUser();
        try {
            normalizeCarSpecification(car);
            return carRepository.save(car);
        } finally {
            auditContextService.clearCurrentUser();
        }
    }

    @Override
    public Optional<Car> findById(UUID id) {
        return carRepository.findById(id);
    }

    @Override
    public Optional<Car> findByVin(String vin) {
        return carRepository.findByVin(vin);
    }

    @Override
    public List<Car> findByClientEmail(String email) {
        return carRepository.findByClientEmail(email);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public void deleteCar(UUID id) {
        auditContextService.setCurrentUser();
        try {
            carRepository.deleteById(id);
        } finally {
            auditContextService.clearCurrentUser();
        }
    }

    private void normalizeCarSpecification(Car car) {
        if (car.getSpecification() != null) {
            return;
        }

        requireCarCatalogFields(car);

        CarBrand brand = carBrandRepository.findByName(car.getBrand())
                .orElseGet(() -> {
                    CarBrand newBrand = new CarBrand();
                    newBrand.setName(car.getBrand());
                    return carBrandRepository.save(newBrand);
                });

        CarModel model = carModelRepository.findByBrandAndName(brand, car.getModel())
                .orElseGet(() -> {
                    CarModel newModel = new CarModel();
                    newModel.setBrand(brand);
                    newModel.setName(car.getModel());
                    newModel.setBodyType(car.getBodyType());
                    newModel.setProductionCountry(car.getProductionCountry());
                    return carModelRepository.save(newModel);
                });

        CarSpecification specification = carSpecificationRepository
                .findByModelAndYearAndEngineTypeAndEngineVolumeAndEnginePowerHpAndTransmissionTypeAndDriveTypeAndFuelType(
                        model,
                        car.getYear(),
                        car.getEngineType(),
                        car.getEngineVolume(),
                        car.getEnginePowerHp(),
                        car.getTransmissionType(),
                        car.getDriveType(),
                        car.getFuelType()
                )
                .orElseGet(() -> {
                    CarSpecification newSpecification = new CarSpecification();
                    newSpecification.setModel(model);
                    newSpecification.setYear(car.getYear());
                    newSpecification.setEngineType(car.getEngineType());
                    newSpecification.setEngineVolume(car.getEngineVolume());
                    newSpecification.setEnginePowerHp(car.getEnginePowerHp());
                    newSpecification.setTransmissionType(car.getTransmissionType());
                    newSpecification.setDriveType(car.getDriveType());
                    newSpecification.setFuelType(car.getFuelType());
                    newSpecification.setWeightKg(car.getWeightKg());
                    return carSpecificationRepository.save(newSpecification);
                });

        car.setSpecification(specification);
    }

    private void requireCarCatalogFields(Car car) {
        if (car.getBrand() == null || car.getModel() == null || car.getBodyType() == null
                || car.getYear() == null || car.getEngineType() == null || car.getEngineVolume() == null
                || car.getEnginePowerHp() == null || car.getTransmissionType() == null
                || car.getDriveType() == null || car.getFuelType() == null) {
            throw new IllegalArgumentException("Car specification fields are required");
        }
    }
}
