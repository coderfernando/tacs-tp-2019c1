package ar.edu.utn.tacs.repositories;

import ar.edu.utn.tacs.model.PlaceRegister;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaceRegisteredRepository extends MongoRepository<PlaceRegister, String>{

}
