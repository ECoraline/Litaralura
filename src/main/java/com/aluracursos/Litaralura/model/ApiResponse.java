package com.aluracursos.Litaralura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private List<LibroDTO> results;

    // Getters y Setters
    public List<LibroDTO> getResults() {
        return results;
    }

    public void setResults(List<LibroDTO> results) {
        this.results = results;
    }
}
