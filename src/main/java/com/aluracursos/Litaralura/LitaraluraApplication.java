package com.aluracursos.Litaralura;

import com.aluracursos.Litaralura.service.ConsumoAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LitaraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LitaraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoAPI = new ConsumoAPI();
		var json = consumoAPI.consumir("https://gutendex.com/books/?page=3&search=dickens");
		System.out.println(json);
	}
}
