package ar.edu.utn.tacs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BackendApplication {

	@Autowired
    private UserRepository ur;

    @Bean
    CommandLineRunner createAdmin() throws Exception {
        return args -> {
			Users admin = new Users("admin", "$2a$10$AjHGc4x3Nez/p4ZpvFDWeO6FGxee/cVqj5KHHnHfuLnIOzC5ag4fm", true);
			ur.deleteAll();
			ur.save(admin);
		};
    }

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("---- TACS backend started at "
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "  ----");
	}
}
