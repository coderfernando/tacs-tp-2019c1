package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.model.PlaceRegister;
import ar.edu.utn.tacs.model.places.Venue;
import ar.edu.utn.tacs.repositories.PlaceRegisteredRepository;
import ar.edu.utn.tacs.repositories.UserRepository;
import ar.edu.utn.tacs.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.model.PlacesList;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
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

    @GetMapping("users/interestedin/{venueid}")
    public List<Users> GetUsersInterestedIn(@PathVariable("venueid") String venueId) {

        List<Users> interestedUsers = userRepository.findAll().stream()
                                        .filter(user -> user.getPlacesLists().stream()
                                              .anyMatch(placeList -> placeList.getPlaces().stream()
                                                      .anyMatch(venue -> venue.getAdditionalProperties().get("foursquareId")
                                                              .equals(venueId))))
                                        .collect(Collectors.toList());

        return interestedUsers;
    }

    @GetMapping("placesregistered")
    public List<Venue> GetPlacesRegistered(@RequestParam(name="dateFrom", required = false) Date dateFrom) {

        List<PlaceRegister> registeredPlaces = placeRegisteredRepository.findAll();
        List<Venue> places;

        Date dateFromNoTime = removeTime(dateFrom);

            places = (dateFrom != null)

            ? registeredPlaces.stream()
                    .filter(rp -> rp.getRegisteredDate().after(dateFromNoTime))
                    .map(rp -> rp.getVenue())
                    .collect(Collectors.toList())

            : registeredPlaces.stream().map(rp -> rp.getVenue()).collect(Collectors.toList());

        return places;
    }

    private Date removeTime(Date date) {

        if (date == null)
            return null;

        Instant dayInst = date.toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate()
                        .atStartOfDay(ZoneId.systemDefault()).toInstant();

        return Date.from(dayInst);
    }

}
