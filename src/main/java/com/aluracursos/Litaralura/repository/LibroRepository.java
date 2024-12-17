package com.aluracursos.Litaralura.repository;

import com.aluracursos.Litaralura.model.Autor;
import com.aluracursos.Litaralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByAutorId(Long autorId);

    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l")
    List<Libro> todosLosLibros();

    @Query("SELECT a FROM Autor a")
    List<Autor> todosLosAutores();

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> buscarPorIdioma(@Param("idioma") String idioma);

}