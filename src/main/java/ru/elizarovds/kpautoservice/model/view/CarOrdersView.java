package ru.elizarovds.kpautoservice.model.view;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "car_orders_view")
@Immutable
public class CarOrdersView {
    @Id
    private UUID id;
    
    private String vin;
    private String brand;
    private String model;
    private Short year;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_phone")
    private String clientPhone;
    
    @Column(name = "order_id")
    private UUID orderId;
    
    @Column(name = "order_status")
    private String orderStatus;
    
    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;
    
    @Column(name = "total_services")
    private Integer totalServices;
    
    @Column(name = "total_cost")
    private BigDecimal totalCost;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Short getYear() { return year; }
    public void setYear(Short year) { this.year = year; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
    public Integer getTotalServices() { return totalServices; }
    public void setTotalServices(Integer totalServices) { this.totalServices = totalServices; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
}
