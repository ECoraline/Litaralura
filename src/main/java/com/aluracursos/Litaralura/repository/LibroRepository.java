package com.aluracursos.Litaralura.repository;

import com.aluracursos.Litaralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByAutorId(Long autorId);

    Optional<Libro> findByTitulo(String titulo);
}
