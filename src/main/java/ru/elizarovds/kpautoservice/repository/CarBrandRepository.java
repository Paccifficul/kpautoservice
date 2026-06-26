package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.CarBrand;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, UUID> {
    Optional<CarBrand> findByName(String name);
}
