package com.javarush.lesson12.convertor;

import com.javarush.khmelov.entity.Password;
import jakarta.persistence.AttributeConverter;

import java.sql.Blob;

public class DemoConvertor implements AttributeConverter<Password,String> {

    @Override
    public String convertToDatabaseColumn(Password password) {
        return password.getValue().toString(); //some logic to Db Type
    }

    @Override
    public Password convertToEntityAttribute(String string) {
        return Password.builder().value(string).build(); //some logic from Db Type
    }
}
