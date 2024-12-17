package com.aluracursos.Litaralura;

import com.aluracursos.Litaralura.Principal.Principal;
import com.aluracursos.Litaralura.repository.AutorRepository;
import com.aluracursos.Litaralura.repository.LibroRepository;
import com.aluracursos.Litaralura.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LitaraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LitaraluraApplication.class, args);
	}
	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraElMenu();
	}
}
