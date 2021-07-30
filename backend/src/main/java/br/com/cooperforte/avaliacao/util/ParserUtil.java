package br.com.cooperforte.avaliacao.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ParserUtil<T> {

    final Class<T> typeParameterClass;

    public ParserUtil(Class<T> typeParameterClass) {

        this.typeParameterClass = typeParameterClass;
    }

    public T convertToObject(String value) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(value, typeParameterClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
