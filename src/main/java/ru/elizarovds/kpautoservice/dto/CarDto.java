package ru.elizarovds.kpautoservice.dto;

import lombok.Data;
import ru.elizarovds.kpautoservice.model.Car;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CarDto {
    private UUID id;
    private String vin;
    private String brand;
    private String model;
    private Short year;
    private Car.EngineType engineType;
    private BigDecimal engineVolume;
    private Short enginePowerHp;
    private Car.TransmissionType transmissionType;
    private Car.DriveType driveType;
    private Car.FuelType fuelType;
    private String bodyType;
    private Integer weightKg;
    private String productionCountry;
    private Long mileage;
    private String color;
    private UUID clientId;
}