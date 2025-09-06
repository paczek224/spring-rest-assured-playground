package com.paczek.demoRest.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DataMapper {

    public static <E> E mapToDto(String content, TypeReference<E> valueTypeRef) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content.getBytes(), valueTypeRef);
    }
    public static <E> String writeValueAsString(E body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(body);
    }
}
