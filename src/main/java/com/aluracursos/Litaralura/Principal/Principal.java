package com.aluracursos.Litaralura.Principal;

import com.aluracursos.Litaralura.exception.LibroDuplicadoException;
import com.aluracursos.Litaralura.model.Libro;
import com.aluracursos.Litaralura.model.LibroDTO;
import com.aluracursos.Litaralura.repository.LibroRepository;
import com.aluracursos.Litaralura.service.ConsumoAPI;
import com.aluracursos.Litaralura.service.ConvierteDatos;
import com.aluracursos.Litaralura.service.LibroService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    LibroRepository repositorio;

    public Principal(LibroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
            }
        }
    }

    private void buscarLibroWeb() {
        System.out.println("Introduce el nombre del autor o del libro");
        var busqueda = teclado.nextLine();
        try {
            // Buscar si el libro ya existe en la base de datos
            Optional<Libro> libroBase = repositorio.findByTitulo(busqueda);
            if (libroBase.isPresent()) {
                throw new LibroDuplicadoException("El libro ya existe en la base de datos");
            }

            // Consumir la API
            var json = consumoApi.consumir(URL_BASE + busqueda);
            System.out.println(json);

            // Deserializar el JSON a un LibroDTO (primer libro)
            LibroDTO datos = conversor.obtenerDatos(json, LibroDTO.class);

            // Asegurarse de que idiomas no sea null o vacío
            List<String> idiomas = datos.idiomas();
            if (idiomas.isEmpty()) {
                System.out.println("El libro no tiene idiomas disponibles.");
            } else {
                String idiomaPrincipal = idiomas.get(0);  // Acceder al primer idioma
                System.out.println("Idioma principal: " + idiomaPrincipal);
            }

            // Crear la entidad Libro a partir de LibroDTO
            Libro libro = new Libro(datos, datos.autores().get(0));
            System.out.println(datos);

            // Guardar el libro en la base de datos
            repositorio.save(libro);
            System.out.println("Libro guardado con éxito.");

        } catch (LibroDuplicadoException e) {
            System.out.println(e.getMessage());  // Mostrar mensaje específico para libro duplicado
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

}