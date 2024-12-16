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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    // Constructor que recibe un LibroDTO
    public Libro(LibroDTO libroD, AutorDTO autor) {
        this.titulo = libroD.titulo();
        this.idioma = libroD.idiomas().get(0);
        this.numeroDescargas = libroD.numeroDescargas() != null ? libroD.numeroDescargas() : 0;
        this.autor = new Autor(autor);
    }

//    public Libro(LibroDTO datos) {
//        this.titulo = datos.titulo();
//        this.idioma = datos.idiomas().get(0);
//        this.numeroDescargas = datos.numeroDescargas();
//        this.autor = new Autor(datos.autores().get(0));
//    }

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
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", numeroDescargas=" + numeroDescargas +
                ", autor=" + autor +
                '}';
    }
}
