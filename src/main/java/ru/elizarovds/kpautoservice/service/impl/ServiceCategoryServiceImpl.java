package ru.elizarovds.kpautoservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.elizarovds.kpautoservice.model.ServiceCategory;
import ru.elizarovds.kpautoservice.repository.ServiceCategoryRepository;
import ru.elizarovds.kpautoservice.service.ServiceCategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {
    private final ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @Override
    public List<ServiceCategory> findActiveCategories() {
        return serviceCategoryRepository.findByIsActiveTrue();
    }

    @Override
    public Optional<ServiceCategory> findById(UUID id) {
        return serviceCategoryRepository.findById(id);
    }

    @Override
    public ServiceCategory saveCategory(ServiceCategory category) {
        return serviceCategoryRepository.save(category);
    }
}