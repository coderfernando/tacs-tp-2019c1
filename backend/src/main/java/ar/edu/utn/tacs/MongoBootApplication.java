package ar.edu.utn.tacs;

import ar.edu.utn.tacs.model.User;
import ar.edu.utn.tacs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/*
*
* INFO (fmuraca) esta clase simple está desarrollada para testear la conexión
* a la base de mongodb, deployada en la nube (mongodb atlas)
* parámetros de conexión en : application.properties
*
* Cuando la app esté funcionando, borrar esta clase.
*
*
*
* */

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MongoBootApplication implements CommandLineRunner {

    @Autowired
    UserRepository repository;

//    @Autowired
//    CustomRepository crepo;

    public static void main(String[] args) {
        SpringApplication.run(MongoBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        deleteAll();
        addSampleData();
        listAll();
        findFirst();
//        findByRegex();
    }

    public void deleteAll() {
        System.out.println("Deleting all records..");
        repository.deleteAll();
    }

    public void addSampleData() {
        System.out.println("Adding sample data");
        repository.save(new User("Jack Bauer", "New York", true));
        repository.save(new User("Harvey Spectre", "London", true));
        repository.save(new User("Mike Ross", "New Jersey", false));
        repository.save(new User("Louise Litt", "Kathmandu", false));
    }

    public void listAll() {
        System.out.println("Listing sample data");
        repository.findAll().forEach(u -> System.out.println(u));
    }

    public void findFirst() {
        System.out.println("Finding first by Name");
        User u = repository.findFirstByName("Louise Litt");
        System.out.println(u);
    }

//    public void findByRegex() {
//        System.out.println("Finding by Regex - All with address starting with ^New");
//        repository.findCustomByRegExAddress("^New").forEach(u -> System.out.println(u));
//    }
}