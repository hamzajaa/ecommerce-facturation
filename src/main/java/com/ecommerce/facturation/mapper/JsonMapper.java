package com.ecommerce.facturation.mapper;

import com.ecommerce.facturation.dto.CommandItemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.activemq.filter.FunctionCallExpression;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonMapper {

    @SneakyThrows(JsonProcessingException.class)
    public <E> E convertJsonToObject(String messageJson, Class<E> eClass) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        E result = objectMapper
                .readValue(messageJson, eClass);

        return result;
    }


    @SneakyThrows(JsonProcessingException.class)
    public String convertObjectToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();

        String result = objectMapper
                .writeValueAsString(object);

        return result;
    }

    @SneakyThrows(JsonProcessingException.class)
    public <E> List<String> convertObjectsToJson(List<E> objects) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<String> stringList = new ArrayList<>();

        for (E object : objects) {
            String result = objectMapper
                    .writeValueAsString(objects);
            stringList.add(result);
        }


        return stringList;

    }

    @SneakyThrows(JsonProcessingException.class)
    public <E> List<E> convertJsonToObjects(String messageJson, Class<E> eClass) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<E> result = objectMapper
                .readValue(messageJson, objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, eClass));

        return result;
    }

    @SneakyThrows(JsonProcessingException.class)
    public <E> List<E> convertJsonToObjects(List<String> messageJson, Class<E> eClass) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<E> eList = new ArrayList<>();
        for (String s : messageJson) {
            E result = objectMapper
                    .readValue(s, eClass);
        }

        return eList;
    }

}
