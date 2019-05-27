package ar.edu.utn.tacs.repositories;

import ar.edu.utn.tacs.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String>{

    Users findFirstByName(String name);
}
