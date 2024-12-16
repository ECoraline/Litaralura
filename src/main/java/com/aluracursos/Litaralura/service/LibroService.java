package com.aluracursos.Litaralura.service;

import com.aluracursos.Litaralura.exception.LibroDuplicadoException;
import com.aluracursos.Litaralura.model.Autor;
import com.aluracursos.Litaralura.model.Libro;
import com.aluracursos.Litaralura.model.LibroDTO;
import com.aluracursos.Litaralura.repository.AutorRepository;
import com.aluracursos.Litaralura.repository.LibroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    @Autowired
    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

//    @Transactional
//    public Libro guardarLibroConAutor(Libro libro, Autor autor) {
//        autorRepository.save(autor); // Guardar el autor primero
//        libro.setAutor(autor); // Asignar el autor al libro
//        return libroRepository.save(libro); // Guardar el libro
//    }
}
