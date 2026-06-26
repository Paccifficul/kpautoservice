package ru.elizarovds.kpautoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.elizarovds.kpautoservice.model.ServiceOrder;
import ru.elizarovds.kpautoservice.service.ServiceOrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class ServiceOrderController {
    private final ServiceOrderService serviceOrderService;

    @Autowired
    public ServiceOrderController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    @GetMapping
    @PreAuthorize("hasRole('employee')")
    public ResponseEntity<List<ServiceOrder>> getAllOrders() {
        return ResponseEntity.ok(serviceOrderService.findAll());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<ServiceOrder>> getMyOrders(Authentication auth) {
        return ResponseEntity.ok(serviceOrderService.findByClientEmail(auth.getName()));
    }

    @GetMapping("/car/{vin}")
    public ResponseEntity<List<ServiceOrder>> getOrdersByCarVin(@PathVariable String vin, Authentication auth) {
        List<ServiceOrder> orders = serviceOrderService.findByCarVin(vin);
        
        // Клиент может видеть только заказы на свои автомобили
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_client"))) {
            List<ServiceOrder> userOrders = serviceOrderService.findByClientEmail(auth.getName());
            orders = orders.stream()
                    .filter(order -> userOrders.stream()
                            .anyMatch(userOrder -> userOrder.getId().equals(order.getId())))
                    .toList();
        }
        
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<ServiceOrder> createOrder(@RequestBody ServiceOrder order) {
        return ResponseEntity.ok(serviceOrderService.createOrder(order));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('employee')")
    public ResponseEntity<ServiceOrder> updateOrderStatus(
            @PathVariable UUID id, 
            @RequestParam ServiceOrder.Status status) {
        return ResponseEntity.ok(serviceOrderService.updateOrderStatus(id, status));
    }
}