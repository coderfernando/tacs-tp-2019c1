package ar.edu.utn.tacs.repositories;

import ar.edu.utn.tacs.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{
}
