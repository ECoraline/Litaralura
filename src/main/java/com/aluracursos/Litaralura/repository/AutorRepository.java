package com.aluracursos.Litaralura.repository;

import com.aluracursos.Litaralura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Método para buscar un autor por su nombre
    Autor findByNombre(String nombre);
}