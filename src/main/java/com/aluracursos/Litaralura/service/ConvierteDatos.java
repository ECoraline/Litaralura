package com.aluracursos.Litaralura.service;

import com.aluracursos.Litaralura.model.ApiResponse;
import com.aluracursos.Litaralura.model.LibroDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            // Deserializa el JSON en la clase ApiResponse, que contiene la lista de libros
            ApiResponse apiResponse = objectMapper.readValue(json, ApiResponse.class);
            // Verificar si la lista de resultados no está vacía
            if (apiResponse != null && apiResponse.getResults() != null && !apiResponse.getResults().isEmpty()) {
                // Obtener el primer libro
                LibroDTO libroDTO = apiResponse.getResults().get(0);  // Obtiene el primer libro de la lista
                System.out.println("Primer libro: " + libroDTO);
                return (T) libroDTO;  // Devuelve el primer libro
            } else {
                throw new RuntimeException("No se encontraron libros.");
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al deserializar JSON: " + e.getMessage(), e);
        }
    }
}