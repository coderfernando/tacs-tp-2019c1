package ar.utn.edu.tacs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TacsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacsApplication.class, args);
		System.out.println("------------WAAKE UP----------");
	}
}
