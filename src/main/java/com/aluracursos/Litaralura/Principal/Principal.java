package com.aluracursos.Litaralura.Principal;

import com.aluracursos.Litaralura.exception.LibroDuplicadoException;
import com.aluracursos.Litaralura.model.Autor;
import com.aluracursos.Litaralura.model.Libro;
import com.aluracursos.Litaralura.model.LibroDTO;
import com.aluracursos.Litaralura.repository.AutorRepository;
import com.aluracursos.Litaralura.repository.LibroRepository;
import com.aluracursos.Litaralura.service.ConsumoAPI;
import com.aluracursos.Litaralura.service.ConvierteDatos;
import com.aluracursos.Litaralura.service.LibroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    List<Libro> libros = null;

    LibroRepository repositorio;

//    public Principal(LibroRepository repositorio) {
//        this.repositorio = repositorio;
//    }
    AutorRepository repositorioAutor;


    public Principal(LibroRepository repositorio, AutorRepository repositorioAutor) {
        this.repositorio = repositorio;
        this.repositorioAutor = repositorioAutor;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros
                    2 - Mostrar los libros guardados
                    3 - Mostrar autores registrados
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    buscarTodosLosLibros();
                    break;
                case 3:
                    buscarAutoresRegistrados();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private void buscarAutoresRegistrados() {
        List<Autor> autores = repositorio.todosLosAutores();
        System.out.println("Autores encontrados:");
        autores.forEach(System.out::println);
    }
    private void buscarLibroWeb() {
        System.out.println("Introduce el nombre del libro a buscar");
        var busqueda = teclado.nextLine();
        try {
            // Buscar si el libro ya existe en la base de datos
            Optional<Libro> libroBase = repositorio.findByTituloContainsIgnoreCase(busqueda);
            if (libroBase.isPresent()) {
                throw new LibroDuplicadoException("El libro ya existe en la base de datos");
            }

            // Consumir la API
            var json = consumoApi.consumir(URL_BASE + busqueda.replace(" ", "%20"));
            System.out.println(json);

            // Deserializar el JSON a un LibroDTO (primer libro)
            LibroDTO datos = conversor.obtenerDatos(json, LibroDTO.class);

            // Verificar si el autor ya existe en la base de datos
            Optional<Autor> autorExistente = repositorioAutor.findByNombreIgnoreCase(datos.autores().get(0).nombre());
            Autor autor;
            if (autorExistente.isPresent()) {
                autor = autorExistente.get(); // Usar el autor existente
            } else {
                autor = new Autor(datos.autores().get(0)); // Crear nuevo autor si no existe
                autor = repositorioAutor.save(autor); // Guardar el nuevo autor para que esté gestionado
            }

            // Crear la entidad Libro a partir de LibroDTO
            Libro libro = new Libro(datos);
            // Asociar el autor al libro
            libro.setAutor(autor);

            // Guardar el libro en la base de datos (el autor se guarda automáticamente por cascada)
            repositorio.save(libro);
            System.out.println("Libro guardado con éxito.");

        } catch (LibroDuplicadoException e) {
            System.out.println(e.getMessage());  // Mostrar mensaje específico para libro duplicado
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

//    private void buscarLibroWeb() {
//        System.out.println("Introduce el nombre del libro a buscar");
//        var busqueda = teclado.nextLine();
//        try {
//            // Buscar si el libro ya existe en la base de datos
//            Optional<Libro> libroBase = repositorio.findByTituloContainsIgnoreCase(busqueda);
//            if (libroBase.isPresent()) {
//                throw new LibroDuplicadoException("El libro ya existe en la base de datos");
//            }
//
//            // Consumir la API
//            var json = consumoApi.consumir(URL_BASE + busqueda.replace(" ", "%20"));
//            System.out.println(json);
//
//            // Deserializar el JSON a un LibroDTO (primer libro)
//            LibroDTO datos = conversor.obtenerDatos(json, LibroDTO.class);
//
//            // Verificar si el autor ya existe en la base de datos
//            Autor autorExistente = repositorioAutor.findByNombreIgnoreCase(datos.autores().get(0).nombre()).get();
//
//            if (autorExistente != null) {
//                System.out.println("Autor encontrado: " + autorExistente);
//                // Crear la entidad Libro a partir de LibroDTO
//                Libro libro = new Libro(datos, autorExistente);
//                 // Asociar el autor existente al libro
//
//                // Guardar el libro en la base de datos (el autor no se vuelve a guardar porque ya existe)
//                repositorio.save(libro);
//                System.out.println("Libro guardado con éxito.");
//            } else {
//                // Si el autor no existe, creamos uno nuevo
//                Autor autor = new Autor(datos.autores().get(0)); // Crear nuevo autor si no existe
//
//                // Crear la entidad Libro a partir de LibroDTO y asociar el autor
//                Libro libro = new Libro(datos, autor);
//
//                // Guardar el libro en la base de datos (el autor se guarda automáticamente por cascada)
//                repositorio.save(libro);
//                System.out.println("Libro guardado con éxito.");
//            }
//
//        } catch (LibroDuplicadoException e) {
//            System.out.println(e.getMessage());  // Mostrar mensaje específico para libro duplicado
//        } catch (Exception e) {
//            System.out.println("Ha ocurrido un error: " + e.getMessage());
//        }
//    }




    private void buscarLibroEnBase() {

        System.out.println("Introduce el nombre del autor o del libro");
        var busqueda = teclado.nextLine();
        Optional<Libro> libroBase = repositorio.findByTituloContainsIgnoreCase(busqueda);
        if (libroBase.isPresent()) {
            System.out.println("Libro encontrado: " + libroBase.get());
        } else {
            System.out.println("Libro no encontrado.");
        }
    }
    private void buscarTodosLosLibros() {
        List<Libro> libros = repositorio.findAll();
        System.out.println("Libros encontrados:");
        libros.forEach(System.out::println);
    }
}