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
                    Elije una opción:
                    1 - Buscar libros
                    2 - Mostrar los libros guardados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en cierto año
                    5 - Mostrar libros por idioma
                    
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
                case 4:
                    buscarAutoresVivosEnAnio();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
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
    //metodo 1
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
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
    //metodo 2
    private void buscarTodosLosLibros() {
        List<Libro> libros = repositorio.todosLosLibros();
        System.out.println("Libros encontrados:");
        libros.forEach(l -> System.out.println("---------- Libro --------------\n" +
                "Título: " + l.getTitulo() + "\n" +
                "Idioma: " + l.getIdioma() + "\n" +
                "Número de descargas: " + l.getNumeroDescargas() + "\n" +
                "Autor: " + l.getAutor().getNombre() +
                "\n---------------------" + "\n"));
    }
    //metodo 3
    private void buscarAutoresRegistrados() {
        List<Autor> autores = repositorio.todosLosAutores();
        System.out.println("Autores encontrados: \n");
        autores.forEach(System.out::println);
    }
    //metodo 4
    private void buscarAutoresVivosEnAnio() {
        System.out.println("Introduce el año a buscar");
        var busqueda = teclado.nextInt();
        List<Autor> autores = repositorioAutor.encontrarAutoresPorAnio(busqueda);
        System.out.println("Autores encontrados:\n");
        autores.forEach(System.out::println);
    }
    //metodo 5
    private void buscarLibrosPorIdioma() {
        List<Libro> libros = null;
        boolean opcionValida = false;

        while (!opcionValida) {
            System.out.println("Elige un idioma: \n" +
                    "es - Español\n" +
                    "en - Inglés\n" +
                    "fr - Francés\n" +
                    "de - Alemán\n" +
                    "pt - Portugués\n");

            var idioma = teclado.nextLine();

            switch (idioma) {
                case "es":
                case "en":
                case "fr":
                case "de":
                case "pt":
                    libros = repositorio.buscarPorIdioma(idioma);
                    opcionValida = true;
                    break;
                default:
                    System.out.println("Idioma no válido. Por favor, selecciona una opción correcta.\n");
                    break;
            }
        }
        // Mostrar los libros encontrados
        System.out.println("Libros encontrados: \n");
        libros.forEach(System.out::println);
    }

}