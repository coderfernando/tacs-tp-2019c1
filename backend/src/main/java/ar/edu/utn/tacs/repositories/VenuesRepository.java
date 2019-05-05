package ar.edu.utn.tacs.repositories;

import ar.edu.utn.tacs.model.places.Venue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VenuesRepository extends MongoRepository<Venue, String> {

}
