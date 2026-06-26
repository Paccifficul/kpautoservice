package ru.elizarovds.kpautoservice.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.elizarovds.kpautoservice.model.Car.FuelType;

@Converter(autoApply = false)
public class FuelTypeConverter implements AttributeConverter<FuelType, String> {

    @Override
    public String convertToDatabaseColumn(FuelType fuelType) {
        if (fuelType == null) {
            return null;
        }
        return fuelType.getValue();
    }

    @Override
    public FuelType convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        for (FuelType type : FuelType.values()) {
            if (type.getValue().equals(dbData)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown fuel type: " + dbData);
    }
}