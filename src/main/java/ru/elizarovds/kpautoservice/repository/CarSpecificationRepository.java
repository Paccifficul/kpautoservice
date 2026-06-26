package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.Car;
import ru.elizarovds.kpautoservice.model.CarModel;
import ru.elizarovds.kpautoservice.model.CarSpecification;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarSpecificationRepository extends JpaRepository<CarSpecification, UUID> {
    Optional<CarSpecification> findByModelAndYearAndEngineTypeAndEngineVolumeAndEnginePowerHpAndTransmissionTypeAndDriveTypeAndFuelType(
            CarModel model,
            Short year,
            Car.EngineType engineType,
            BigDecimal engineVolume,
            Short enginePowerHp,
            Car.TransmissionType transmissionType,
            Car.DriveType driveType,
            Car.FuelType fuelType
    );
}
