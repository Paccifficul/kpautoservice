package ru.elizarovds.kpautoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.elizarovds.kpautoservice.model.Car;
import ru.elizarovds.kpautoservice.service.CarService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    @PreAuthorize("hasRole('employee')")
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.findAll());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<Car>> getMyCars(Authentication auth) {
        return ResponseEntity.ok(carService.findByClientEmail(auth.getName()));
    }

    @GetMapping("/vin/{vin}")
    public ResponseEntity<Car> getCarByVin(@PathVariable String vin, Authentication auth) {
        Car car = carService.findByVin(vin)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        
        // Клиент может видеть только свои автомобили
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_client"))) {
            List<Car> userCars = carService.findByClientEmail(auth.getName());
            if (userCars.stream().noneMatch(c -> c.getVin().equals(vin))) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        return ResponseEntity.ok(car);
    }

    @PostMapping
    @PreAuthorize("hasRole('employee')")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.ok(carService.saveCar(car));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('employee')")
    public ResponseEntity<Car> updateCar(@PathVariable UUID id, @RequestBody Car car) {
        car.setId(id);
        return ResponseEntity.ok(carService.saveCar(car));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('employee')")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }
}
