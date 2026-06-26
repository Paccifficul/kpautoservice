package ru.elizarovds.kpautoservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.elizarovds.kpautoservice.model.ServiceOrder;
import ru.elizarovds.kpautoservice.repository.ServiceOrderRepository;
import ru.elizarovds.kpautoservice.service.ServiceOrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {
    private final ServiceOrderRepository serviceOrderRepository;

    @Autowired
    public ServiceOrderServiceImpl(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public ServiceOrder createOrder(ServiceOrder order) {
        return serviceOrderRepository.save(order);
    }

    @Override
    public Optional<ServiceOrder> findById(UUID id) {
        return serviceOrderRepository.findById(id);
    }

    @Override
    public List<ServiceOrder> findByClientEmail(String email) {
        return serviceOrderRepository.findByClientEmail(email);
    }

    @Override
    public List<ServiceOrder> findByCarVin(String vin) {
        return serviceOrderRepository.findByCarVin(vin);
    }

    @Override
    public List<ServiceOrder> findAll() {
        return serviceOrderRepository.findAll();
    }

    @Override
    public ServiceOrder updateOrderStatus(UUID orderId, ServiceOrder.Status status) {
        ServiceOrder order = serviceOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        if (status == ServiceOrder.Status.in_progress) {
            order.setStartedAt(LocalDateTime.now());
        } else if (status == ServiceOrder.Status.completed) {
            order.setActualEnd(LocalDateTime.now());
        }
        
        return serviceOrderRepository.save(order);
    }
}