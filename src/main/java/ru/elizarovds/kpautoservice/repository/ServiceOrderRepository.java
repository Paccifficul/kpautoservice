package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.ServiceOrder;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, UUID> {
    @Override
    @EntityGraph(attributePaths = {"car", "car.client", "car.client.user", "car.specification", "car.specification.model", "car.specification.model.brand", "employee", "employee.user"})
    List<ServiceOrder> findAll();

    @EntityGraph(attributePaths = {"car", "car.client", "car.client.user", "car.specification", "car.specification.model", "car.specification.model.brand", "employee", "employee.user"})
    List<ServiceOrder> findByCarId(UUID carId);

    @EntityGraph(attributePaths = {"car", "car.client", "car.client.user", "car.specification", "car.specification.model", "car.specification.model.brand", "employee", "employee.user"})
    @Query("SELECT so FROM ServiceOrder so WHERE so.car.client.user.email = :email")
    List<ServiceOrder> findByClientEmail(@Param("email") String email);

    @EntityGraph(attributePaths = {"car", "car.client", "car.client.user", "car.specification", "car.specification.model", "car.specification.model.brand", "employee", "employee.user"})
    @Query("SELECT so FROM ServiceOrder so WHERE so.car.vin = :vin")
    List<ServiceOrder> findByCarVin(@Param("vin") String vin);

    @EntityGraph(attributePaths = {"car", "car.client", "car.client.user", "car.specification", "car.specification.model", "car.specification.model.brand", "employee", "employee.user"})
    List<ServiceOrder> findByStatus(ServiceOrder.Status status);
}
