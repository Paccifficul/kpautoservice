package ru.elizarovds.kpautoservice.service;

import ru.elizarovds.kpautoservice.model.ServiceCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceCategoryService {
    List<ServiceCategory> findActiveCategories();
    Optional<ServiceCategory> findById(UUID id);
    ServiceCategory saveCategory(ServiceCategory category);
}