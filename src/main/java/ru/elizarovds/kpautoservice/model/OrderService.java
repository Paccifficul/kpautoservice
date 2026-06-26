package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_services")
public class OrderService {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private ServiceOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(name = "duration_actual_min")
    private Short durationActualMin;

    @Column(name = "price_charged", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceCharged;

    @Column(columnDefinition = "TEXT")
    private String notes;

    /**
     * ==GETTERS==
     */

    public UUID getId() { return id; }
    public ServiceOrder getOrder() { return order; }
    public Service getService() { return service; }
    public Short getDurationActualMin() { return durationActualMin; }
    public BigDecimal getPriceCharged() { return priceCharged; }
    public String getNotes() { return notes; }

    /**
     * ==SETTERS==
     */

    public void setId(UUID id) { this.id = id; }
    public void setOrder(ServiceOrder order) { this.order = order; }
    public void setService(Service service) { this.service = service; }
    public void setDurationActualMin(Short durationActualMin) { this.durationActualMin = durationActualMin; }
    public void setPriceCharged(BigDecimal priceCharged) { this.priceCharged = priceCharged; }
    public void setNotes(String notes) { this.notes = notes; }
}