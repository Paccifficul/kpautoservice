package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.Service;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<Service, UUID> {
    List<Service> findByCategoryId(UUID categoryId);
    
    @Query("SELECT s, COUNT(os) as usage_count FROM Service s " +
           "LEFT JOIN OrderService os ON s.id = os.service.id " +
           "GROUP BY s ORDER BY usage_count DESC")
    List<Object[]> findServicePopularity();
}