package com.javarush.lesson12.converter;

import com.javarush.kovalinsky.entity.Password;
import jakarta.persistence.AttributeConverter;

public class DemoConverter implements AttributeConverter<Password, String> {

    @Override
    public String convertToDatabaseColumn(Password attribute) {
        return null;
    }

    @Override
    public Password convertToEntityAttribute(String dbData) {
        return null;
    }
}
