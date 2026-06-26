package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, length = 17)
    private String vin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_id")
    private CarSpecification specification;

    @Column(nullable = false)
    private Long mileage;

    @Column(length = 30)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    private String brand;

    @Transient
    private String model;

    @Transient
    private Short year;

    @Transient
    private EngineType engineType;

    @Transient
    private BigDecimal engineVolume;

    @Transient
    private Short enginePowerHp;

    @Transient
    private TransmissionType transmissionType;

    @Transient
    private DriveType driveType;

    @Transient
    private FuelType fuelType;

    @Transient
    private String bodyType;

    @Transient
    private Integer weightKg;

    @Transient
    private String productionCountry;

    public enum EngineType {
        gasoline, diesel, hybrid, electric
    }

    public enum TransmissionType {
        manual, automatic, cvt, dual_clutch
    }

    public enum DriveType {
        FWD, RWD, AWD
    }

    public enum FuelType {
        AI_92("AI-92"),
        AI_95("AI-95"),
        AI_98("AI-98"),
        AI_100("AI-100"),
        diesel("diesel"),
        electric("electric");
        
        private final String value;
        
        FuelType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }

    /**
     * ==GETTERS==
     */

    public UUID getId() { return id; }
    public String getVin() { return vin; }
    public CarSpecification getSpecification() { return specification; }
    public String getBrand() {
        if (specification != null && specification.getModel() != null && specification.getModel().getBrand() != null) {
            return specification.getModel().getBrand().getName();
        }
        return brand;
    }
    public String getModel() {
        if (specification != null && specification.getModel() != null) {
            return specification.getModel().getName();
        }
        return model;
    }
    public Short getYear() { return specification != null ? specification.getYear() : year; }
    public EngineType getEngineType() { return specification != null ? specification.getEngineType() : engineType; }
    public BigDecimal getEngineVolume() { return specification != null ? specification.getEngineVolume() : engineVolume; }
    public Short getEnginePowerHp() { return specification != null ? specification.getEnginePowerHp() : enginePowerHp; }
    public TransmissionType getTransmissionType() { return specification != null ? specification.getTransmissionType() : transmissionType; }
    public DriveType getDriveType() { return specification != null ? specification.getDriveType() : driveType; }
    public FuelType getFuelType() { return specification != null ? specification.getFuelType() : fuelType; }
    public String getBodyType() {
        if (specification != null && specification.getModel() != null) {
            return specification.getModel().getBodyType();
        }
        return bodyType;
    }
    public Integer getWeightKg() { return specification != null ? specification.getWeightKg() : weightKg; }
    public String getProductionCountry() {
        if (specification != null && specification.getModel() != null) {
            return specification.getModel().getProductionCountry();
        }
        return productionCountry;
    }
    public Long getMileage() { return mileage; }
    public String getColor() { return color; }
    public Client getClient() { return client; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    /**
     * ==SETTERS==
     */

    public void setId(UUID id) { this.id = id; }
    public void setVin(String vin) { this.vin = vin; }
    public void setSpecification(CarSpecification specification) { this.specification = specification; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setYear(Short year) { this.year = year; }
    public void setEngineType(EngineType engineType) { this.engineType = engineType; }
    public void setEngineVolume(BigDecimal engineVolume) { this.engineVolume = engineVolume; }
    public void setEnginePowerHp(Short enginePowerHp) { this.enginePowerHp = enginePowerHp; }
    public void setTransmissionType(TransmissionType transmissionType) { this.transmissionType = transmissionType; }
    public void setDriveType(DriveType driveType) { this.driveType = driveType; }
    public void setFuelType(FuelType fuelType) { this.fuelType = fuelType; }
    public void setBodyType(String bodyType) { this.bodyType = bodyType; }
    public void setWeightKg(Integer weightKg) { this.weightKg = weightKg; }
    public void setProductionCountry(String productionCountry) { this.productionCountry = productionCountry; }
    public void setMileage(Long mileage) { this.mileage = mileage; }
    public void setColor(String color) { this.color = color; }
    public void setClient(Client client) { this.client = client; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
