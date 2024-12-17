package com.aluracursos.Litaralura.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String nombre;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    private Integer nacimiento;

    private Integer fallecimiento;

    public Autor() {
    }

    public Autor(AutorDTO autorDTO) {
        this.nombre = autorDTO.nombre();
        this.nacimiento = autorDTO.nacimiento();
        this.fallecimiento = autorDTO.fallecimiento();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l ->l.setAutor(this));
        this.libros = libros;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    @Override
    public String toString() {
        return "\n------ Autor-----\n" +
                "Nombre: " +nombre + "\n" +
                "Fecha de Nacimiento: " + nacimiento + "\n" +
                "Fecha de Fallecimiento: " + fallecimiento + "\n" +
                "Libros: " + libros.stream().map(Libro::getTitulo).reduce((a, b) -> a + ", " + b).orElse("")
                + "\n-----------------";
    }
}
