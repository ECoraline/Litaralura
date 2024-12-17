
# Litaralura

**Litaralura** es una aplicación de consola desarrollada en Java utilizando Spring Boot. Este proyecto forma parte del desafío propuesto en el curso de Back-End de Alura Latam / Oracle, dentro del programa ONE. La aplicación interactúa con la API de Gutendex para acceder a una amplia colección de información sobre libros y autores.

## Características

- **Consumo de la API de Gutendex**: Recupera información detallada sobre libros y autores.
- **Almacenamiento en base de datos**: Guarda los datos obtenidos en una base de datos PostgreSQL para su posterior consulta.
- **Consulta de autores**: Ofrece la funcionalidad de listar autores que estaban vivos después de una fecha específica proporcionada por el usuario.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal.
- **Spring Boot**: Framework para facilitar el desarrollo de aplicaciones Java.
- **JPA/Hibernate**: Para la gestión de la persistencia de datos.
- **PostgreSQL**: Base de datos relacional utilizada.
- **Gutendex API**: Fuente de datos de libros y autores.

## Requisitos Previos

- **Java 11** o superior instalado.
- **Maven** para la gestión de dependencias.
- **PostgreSQL** configurado y en ejecución.
- Conexión a internet para acceder a la API de Gutendex.

## Instalación y Ejecución

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/ECoraline/Litaralura.git
   cd Litaralura
   ```

2. **Configurar la base de datos**:

    - Crea una base de datos en PostgreSQL.
    - Actualiza el archivo `application.properties` con las credenciales de tu base de datos:

      ```properties
      spring.datasource.url=jdbc:postgresql://localhost/tu_base_de_datos
      spring.datasource.username=tu_usuario
      spring.datasource.password=tu_contraseña
      ```

3. **Compilar y ejecutar la aplicación**


## Uso

Al ejecutar la aplicación, se presentará un menú en la consola con las siguientes opciones:

1. **Buscar libros**: Solicita un título de libro y lo busca en la API de Gutendex.
2. **Mostrar los libros guardados**: Muestra una lista de todos los libros almacenados en la base de datos
3. **Mostrar autores registrados**: Muestra una lista de todos los autores registrados en la base de datos
4. **Listar autores vivos en cierto año**: Solicita un rango de fechas y muestra los autores que estaban vivos en ese año.
5. **Mostrar libros por idioma**: Permite seleccionar un idioma de una lista predefinida y muestra los libros que has guardado en ese idioma.


Sigue las instrucciones en pantalla para interactuar con la aplicación.



## Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## Agradecimientos

- Alura Latam y Oracle por el desafío y la oportunidad de aprendizaje.
- Gutendex por proporcionar una API pública de libros y autores.
