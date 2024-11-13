package com.fitdine.user.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, String > {

    private static final String Y = "Y";
    private static final String N = "N";

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? Y : N;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return Y.equals(dbData);
    }
}
