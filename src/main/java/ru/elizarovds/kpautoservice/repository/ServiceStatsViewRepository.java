package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.view.ServiceStatsView;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceStatsViewRepository extends JpaRepository<ServiceStatsView, UUID> {
    @Query("SELECT s FROM ServiceStatsView s ORDER BY s.usageCount DESC")
    List<ServiceStatsView> findMostPopularServices();
    
    @Query("SELECT s FROM ServiceStatsView s ORDER BY s.totalRevenue DESC")
    List<ServiceStatsView> findMostProfitableServices();
    
    List<ServiceStatsView> findByCategoryName(String categoryName);
}