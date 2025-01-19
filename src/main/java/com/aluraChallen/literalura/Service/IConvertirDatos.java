package com.aluraChallen.literalura.Service;

public interface IConvertirDatos {
    <T> T obtenerInfoJSON(String json, Class<T> clase);
}
