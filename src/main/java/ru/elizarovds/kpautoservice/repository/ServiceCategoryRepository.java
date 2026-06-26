package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.ServiceCategory;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, UUID> {
    List<ServiceCategory> findByIsActiveTrue();
}