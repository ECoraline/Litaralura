package com.aluracursos.Litaralura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String idioma;

    private Integer numeroDescargas;

    @ManyToOne
    private Autor autor;

    public Libro() {}

    // Constructor que recibe un LibroDTO
//    public Libro(LibroDTO libroD, Autor autor) {
//        this.titulo = libroD.titulo();
//        this.idioma = libroD.idiomas().get(0);
//        this.numeroDescargas = libroD.numeroDescargas() != null ? libroD.numeroDescargas() : 0;
//    }

    public Libro(LibroDTO datos) {
        this.titulo = datos.titulo();
        this.idioma = datos.idiomas().get(0);
        this.numeroDescargas = datos.numeroDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String toString() {
        return "---------- Libro --------------\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor + '\n' +
                "Idioma: " + idioma + '\n' +
                "Numero de Descargas: " + numeroDescargas
                + "\n-----------------------------\n";
    }
}
