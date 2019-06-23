package ar.edu.utn.tacs.util;

import ar.edu.utn.tacs.model.PlaceRegister;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.model.places.Venue;
import ar.edu.utn.tacs.repositories.PlaceRegisteredRepository;
import ar.edu.utn.tacs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Utils {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRegisteredRepository placeRegisteredRepository;

    public Users getLoggedUser (){
        try {
            String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            return userRepository.findFirstByName(username);
        }
        catch (Exception e) {
            return null;
        }
    }

    public PlaceRegister registerPlace (Venue place){

        boolean isNotRegistered = placeRegisteredRepository.findAll().stream()
                                        .filter(pr -> pr.getVenue()
                                                .getAdditionalProperties().get("foursquareId")
                                                .equals(place.getAdditionalProperties().get("foursquareId")))
                                        .count() == 0;

        if (isNotRegistered) {

            PlaceRegister placeRegister = new PlaceRegister();
            placeRegister.setVenue(place);
            placeRegister.setRegisteredDate(new Date());

            placeRegisteredRepository.save(placeRegister);

            return placeRegister;
        }
        return  null;
    }

}
