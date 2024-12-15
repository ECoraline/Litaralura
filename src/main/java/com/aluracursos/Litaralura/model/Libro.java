package com.aluracursos.Litaralura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String titulo;
    String lenguaje;
    long descargas;
}
