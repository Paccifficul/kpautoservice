package ru.elizarovds.kpautoservice.service;

import ru.elizarovds.kpautoservice.model.ServiceOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceOrderService {
    ServiceOrder createOrder(ServiceOrder order);
    Optional<ServiceOrder> findById(UUID id);
    List<ServiceOrder> findByClientEmail(String email);
    List<ServiceOrder> findByCarVin(String vin);
    List<ServiceOrder> findAll();
    ServiceOrder updateOrderStatus(UUID orderId, ServiceOrder.Status status);
}