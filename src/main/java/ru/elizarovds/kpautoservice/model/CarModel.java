package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "car_models", uniqueConstraints = {
        @UniqueConstraint(name = "uk_car_models_brand_name", columnNames = {"brand_id", "name"})
})
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private CarBrand brand;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "body_type", nullable = false, length = 20)
    private String bodyType;

    @Column(name = "production_country", length = 50)
    private String productionCountry;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public CarBrand getBrand() { return brand; }
    public void setBrand(CarBrand brand) { this.brand = brand; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBodyType() { return bodyType; }
    public void setBodyType(String bodyType) { this.bodyType = bodyType; }
    public String getProductionCountry() { return productionCountry; }
    public void setProductionCountry(String productionCountry) { this.productionCountry = productionCountry; }
}
