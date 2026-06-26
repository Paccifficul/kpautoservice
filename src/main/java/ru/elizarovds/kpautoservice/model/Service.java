package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ServiceCategory category;

    @Column(name = "avg_duration_min", nullable = false)
    private Short avgDurationMin;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * ==GETTERS==
     */

    public UUID getId() { return id; }
    public String getName() { return name; }
    public ServiceCategory getCategory() { return category; }
    public Short getAvgDurationMin() { return avgDurationMin; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }

    /**
     * ==SETTERS==
     */

    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(ServiceCategory category) { this.category = category; }
    public void setAvgDurationMin(Short avgDurationMin) { this.avgDurationMin = avgDurationMin; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
}