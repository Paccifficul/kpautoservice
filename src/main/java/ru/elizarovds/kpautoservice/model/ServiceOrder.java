package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "service_orders")
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "planned_end")
    private LocalDateTime plannedEnd;

    @Column(name = "actual_end")
    private LocalDateTime actualEnd;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public enum Status {
        scheduled, in_progress, completed, cancelled
    }

    /**
     * ==GETTERS==
     */

    public UUID getId() { return id; }
    public Car getCar() { return car; }
    public Employee getEmployee() { return employee; }
    public Status getStatus() { return status; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getPlannedEnd() { return plannedEnd; }
    public LocalDateTime getActualEnd() { return actualEnd; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * ==SETTERS==
     */

    public void setId(UUID id) { this.id = id; }
    public void setCar(Car car) { this.car = car; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setStatus(Status status) { this.status = status; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public void setPlannedEnd(LocalDateTime plannedEnd) { this.plannedEnd = plannedEnd; }
    public void setActualEnd(LocalDateTime actualEnd) { this.actualEnd = actualEnd; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}