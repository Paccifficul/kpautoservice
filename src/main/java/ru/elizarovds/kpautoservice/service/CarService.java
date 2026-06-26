package ru.elizarovds.kpautoservice.service;

import ru.elizarovds.kpautoservice.model.Car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarService {
    Car saveCar(Car car);
    Optional<Car> findById(UUID id);
    Optional<Car> findByVin(String vin);
    List<Car> findByClientEmail(String email);
    List<Car> findAll();
    void deleteCar(UUID id);
}