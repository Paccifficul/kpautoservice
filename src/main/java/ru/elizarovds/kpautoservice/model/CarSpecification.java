package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;
import ru.elizarovds.kpautoservice.converter.FuelTypeConverter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "car_specifications", uniqueConstraints = {
        @UniqueConstraint(name = "uk_car_specifications_natural_key", columnNames = {
                "model_id", "year", "engine_type", "engine_volume", "engine_power_hp",
                "transmission_type", "drive_type", "fuel_type"
        })
})
public class CarSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel model;

    @Column(nullable = false)
    private Short year;

    @Enumerated(EnumType.STRING)
    @Column(name = "engine_type", nullable = false)
    private Car.EngineType engineType;

    @Column(name = "engine_volume", nullable = false, precision = 3, scale = 1)
    private BigDecimal engineVolume;

    @Column(name = "engine_power_hp", nullable = false)
    private Short enginePowerHp;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type", nullable = false)
    private Car.TransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "drive_type", nullable = false)
    private Car.DriveType driveType;

    @Convert(converter = FuelTypeConverter.class)
    @Column(name = "fuel_type", nullable = false)
    private Car.FuelType fuelType;

    @Column(name = "weight_kg")
    private Integer weightKg;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public CarModel getModel() { return model; }
    public void setModel(CarModel model) { this.model = model; }
    public Short getYear() { return year; }
    public void setYear(Short year) { this.year = year; }
    public Car.EngineType getEngineType() { return engineType; }
    public void setEngineType(Car.EngineType engineType) { this.engineType = engineType; }
    public BigDecimal getEngineVolume() { return engineVolume; }
    public void setEngineVolume(BigDecimal engineVolume) { this.engineVolume = engineVolume; }
    public Short getEnginePowerHp() { return enginePowerHp; }
    public void setEnginePowerHp(Short enginePowerHp) { this.enginePowerHp = enginePowerHp; }
    public Car.TransmissionType getTransmissionType() { return transmissionType; }
    public void setTransmissionType(Car.TransmissionType transmissionType) { this.transmissionType = transmissionType; }
    public Car.DriveType getDriveType() { return driveType; }
    public void setDriveType(Car.DriveType driveType) { this.driveType = driveType; }
    public Car.FuelType getFuelType() { return fuelType; }
    public void setFuelType(Car.FuelType fuelType) { this.fuelType = fuelType; }
    public Integer getWeightKg() { return weightKg; }
    public void setWeightKg(Integer weightKg) { this.weightKg = weightKg; }
}
