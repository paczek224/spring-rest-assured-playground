package com.paczek.demo.tests.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paczek.demo.app.users.UserDto;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class DataMapper {

    public static <E> E mapToDto(String content, TypeReference<E> valueTypeRef) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content.getBytes(), valueTypeRef);
    }

    public static <E> String writeValueAsString(E body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(body);
    }

    public static <E> E getElement(List<E> list, Predicate<E> predicate) {
        return list
                .stream()
                .filter(predicate)
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }
}
