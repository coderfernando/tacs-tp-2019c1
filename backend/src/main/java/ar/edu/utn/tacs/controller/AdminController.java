package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.model.places.Venue;
import ar.edu.utn.tacs.repositories.PlaceRegisteredRepository;
import ar.edu.utn.tacs.repositories.UserRepository;
import ar.edu.utn.tacs.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.model.PlacesList;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    Utils utils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRegisteredRepository placeRegisteredRepository;

    @GetMapping("users/all")
    public List<Users> GetUsers() {
        List<Users> allUsers = userRepository.findAll();
        return allUsers;
    }

    @GetMapping("users/get/lists/{id}")
    public Long getCountLists(@PathVariable("id") String id) {
        Users usr = userRepository.findById(id).orElse(null);
        return usr.getPlacesLists().stream().count();
    }

    @GetMapping("users/get/user/data/{id}")
    public Users getUserData(@PathVariable("id") String id) {
        Users usr = userRepository.findById(id).orElse(null);
        return usr;
    }

    @GetMapping("users/get/visited/places/{id}")
    public Long getVisitedPlaces(@PathVariable("id") String id) {
        Users usr = userRepository.findById(id).orElse(null);
        return usr.getPlacesLists().stream().map(PlacesList::getVisitedPlacesIds).mapToInt(List::size).count();
    }

    @GetMapping("users/get/last/access/{id}")
    public Date getLastAccess(@PathVariable("id") String id) {
        Users usr = userRepository.findById(id).orElse(null);
        return usr.getLastAccess();
    }

    @GetMapping("interestedin/{venueid}")
    public List<Users> GetUsersInterestedIn(@PathVariable("venueid") String venueId) {

        List<Users> interestedUsers = userRepository.findAll().stream()
                                        .filter(user -> user.getPlacesLists().stream()
                                              .anyMatch(placeList -> placeList.getPlaces().stream()
                                                      .anyMatch(venue -> venue.getId().equals(venueId))))
                                        .collect(Collectors.toList());




        return interestedUsers;
    }

    @GetMapping("placesregistered")
    public List<Venue> GetPlacesRegistered() {

        List<Venue> registeredPlaces = placeRegisteredRepository.findAll().stream().map(pl -> pl.getVenue()).collect(Collectors.toList());

        return registeredPlaces;
    }
}

//    Como administrador quiero poder ver los siguientes datos de un usuario:
// Ejemplo de uso: http://localhost:8099/api/admin/users/get/user/data/5cf003ce57b919479a1f4631

//        - Usuario   ---- DONE  ----- api/admin/users/get/user/data/{id}
//        - Cantidad de listas --- DONE ---- api/admin/users/get/lists/{id}
//        - Cantidad de lugares visitados en sus listas.  ---- DONE  ----- api/admin/users/get/visited/places/{id}
//        - Último acceso ---- DONE ----  api/admin/users/get/last/access/{id}
//
//    Como administrador quiero seleccionar 2 listas de usuarios diferentes y ver si tienen algún lugar en común. -- DONE Estamos devolviendo todos los usuarios con t0do se puede hacer en la UI.


//    Como administrador quiero seleccionar un lugar y ver la cantidad de usuarios que se interesaron en el mismo (lo agregaron a una lista).


//    Como administrador quiero conocer la cantidad total de lugares registrados en el sistema
//        - En el día de hoy
//        - En los últimos 3 días
//        - En la última semana
//        - En el último mes
//        - Desde el inicio de los tiempos

