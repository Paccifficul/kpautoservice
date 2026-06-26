package ru.elizarovds.kpautoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.elizarovds.kpautoservice.model.ServiceCategory;
import ru.elizarovds.kpautoservice.service.ServiceCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class ServiceCategoryController {
    private final ServiceCategoryService serviceCategoryService;

    @Autowired
    public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {
        this.serviceCategoryService = serviceCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceCategory>> getActiveCategories() {
        return ResponseEntity.ok(serviceCategoryService.findActiveCategories());
    }
}