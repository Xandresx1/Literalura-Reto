package com.aluraChallen.literalura.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertirDatos implements IConvertirDatos {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerInfoJSON(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
