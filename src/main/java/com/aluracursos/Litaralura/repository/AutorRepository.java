package com.aluracursos.Litaralura.repository;

import com.aluracursos.Litaralura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Mtodo para buscar un autor por su nombre
    Optional<Autor> findByNombreIgnoreCase(String nombre);

    List<Autor> findAll();
}