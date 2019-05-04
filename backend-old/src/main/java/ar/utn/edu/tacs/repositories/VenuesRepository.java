package ar.utn.edu.tacs.repositories;

import ar.utn.edu.tacs.model.places.Venue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VenuesRepository extends MongoRepository<Venue, String> {

}
