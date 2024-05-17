package com.javarush.lesson12.convertor;

import com.javarush.khmelov.entity.Password;
import jakarta.persistence.AttributeConverter;

import java.sql.Blob;

public class DemoConvertor implements AttributeConverter<Password,String> {

    @Override
    public String convertToDatabaseColumn(Password password) {
        return null;
    }

    @Override
    public Password convertToEntityAttribute(String string) {
        return null;
    }
}
