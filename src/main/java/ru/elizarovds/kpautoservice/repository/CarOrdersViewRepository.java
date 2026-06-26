package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.view.CarOrdersView;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarOrdersViewRepository extends JpaRepository<CarOrdersView, UUID> {
    interface StatusCount {
        String getStatus();
        Long getCount();
    }

    @Query("SELECT c FROM CarOrdersView c WHERE c.clientName LIKE %:clientName%")
    List<CarOrdersView> findByClientNameContaining(@Param("clientName") String clientName);
    
    List<CarOrdersView> findByOrderStatus(String status);
    
    List<CarOrdersView> findByVin(String vin);

    @Query("""
            SELECT c.orderStatus AS status, COUNT(c) AS count
            FROM CarOrdersView c
            WHERE c.orderStatus IS NOT NULL
            GROUP BY c.orderStatus
            """)
    List<StatusCount> countByOrderStatus();
}
