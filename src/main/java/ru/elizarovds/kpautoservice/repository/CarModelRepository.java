package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.CarBrand;
import ru.elizarovds.kpautoservice.model.CarModel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, UUID> {
    Optional<CarModel> findByBrandAndName(CarBrand brand, String name);
}
