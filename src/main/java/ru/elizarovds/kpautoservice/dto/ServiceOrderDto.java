package ru.elizarovds.kpautoservice.dto;

import lombok.Data;
import ru.elizarovds.kpautoservice.model.ServiceOrder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ServiceOrderDto {
    private UUID id;
    private UUID carId;
    private String carVin;
    private UUID employeeId;
    private ServiceOrder.Status status;
    private LocalDateTime scheduledAt;
    private LocalDateTime startedAt;
    private LocalDateTime plannedEnd;
    private LocalDateTime actualEnd;
    private String notes;
    private LocalDateTime createdAt;
}