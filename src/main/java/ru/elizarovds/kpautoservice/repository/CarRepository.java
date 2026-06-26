package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.Car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
    interface GroupCount {
        String getName();
        Long getCount();
    }

    @Override
    @EntityGraph(attributePaths = {"client", "client.user", "specification", "specification.model", "specification.model.brand"})
    List<Car> findAll();

    @EntityGraph(attributePaths = {"client", "client.user", "specification", "specification.model", "specification.model.brand"})
    Optional<Car> findByVin(String vin);

    @EntityGraph(attributePaths = {"client", "client.user", "specification", "specification.model", "specification.model.brand"})
    List<Car> findByClientId(UUID clientId);

    @EntityGraph(attributePaths = {"client", "client.user", "specification", "specification.model", "specification.model.brand"})
    @Query("SELECT c FROM Car c WHERE c.client.user.email = :email")
    List<Car> findByClientEmail(@Param("email") String email);

    @EntityGraph(attributePaths = {"client", "client.user", "specification", "specification.model", "specification.model.brand"})
    @Query("SELECT c FROM Car c WHERE c.specification.model.brand.name = :brand AND c.specification.model.name = :model")
    List<Car> findByBrandAndModel(@Param("brand") String brand, @Param("model") String model);

    @Query("SELECT c.specification.model.brand.name AS name, COUNT(c) AS count FROM Car c GROUP BY c.specification.model.brand.name ORDER BY COUNT(c) DESC")
    List<GroupCount> countByBrand();

    @Query("SELECT CAST(c.specification.year AS string) AS name, COUNT(c) AS count FROM Car c GROUP BY c.specification.year ORDER BY c.specification.year DESC")
    List<GroupCount> countByYear();
}
