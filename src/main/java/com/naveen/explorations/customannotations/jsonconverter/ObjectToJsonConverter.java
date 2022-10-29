package com.naveen.explorations.customannotations.jsonconverter;

import com.naveen.explorations.customannotations.annotations.Init;
import com.naveen.explorations.customannotations.annotations.JsonElement;
import com.naveen.explorations.customannotations.annotations.JsonSerializable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectToJsonConverter {

    private void checkIfSerializable(Object object) throws Exception {
        if (Objects.isNull(object)) {
            throw new Exception("The object to serialize is null");
        }

        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
            throw new Exception("The class "
                    + clazz.getSimpleName()
                    + " is not annotated with JsonSerializable");
        }
    }

    private void initializeObject(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private String getJsonString(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        Map<String, String> jsonElementsMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonElement.class)) {
                jsonElementsMap.put(getKey(field), (String) field.get(object));
            }
        }

        String jsonString = jsonElementsMap.entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":\""
                        + entry.getValue() + "\"")
                .collect(Collectors.joining(","));
        return "{" + jsonString + "}";
    }

    private String getKey(Field field) {
        String value = field.getAnnotation(JsonElement.class)
                .key();
        return value.isEmpty() ? field.getName() : value;
    }

    public String convertToJson(Object object) throws Exception {
        try {
            checkIfSerializable(object);
            initializeObject(object);
            return getJsonString(object);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
