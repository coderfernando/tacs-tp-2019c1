package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.model.PlacesList;
import ar.edu.utn.tacs.model.UserSession;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.model.places.Venue;
import ar.edu.utn.tacs.repositories.UserRepository;
import ar.edu.utn.tacs.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping ("api/me/lists")
public class ListsController {

    private Logger logger = LoggerFactory.getLogger(PlacesController.class);

    @Autowired
    Utils utils;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<PlacesList> getLists() {
//        return UserSession.getInstance().getLists();
        return utils.getLoggedUser().getPlacesLists();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PlacesList create(@RequestBody PlacesList placesList) {
        logger.info("Request to create List name:" + placesList.getName());
        Users usr = utils.getLoggedUser();
        if(usr.getPlacesLists() == null){
            usr.setPlacesLists(new ArrayList<>());
        }
        usr.getPlacesLists().add(placesList);
        userRepository.save(usr);
        return placesList;
//        return UserSession.getInstance().addList(placesList);
    }

    @PatchMapping("/{id}/change-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList changeName(@PathVariable("id") String id, @PathVariable("name") String name) {
        return UserSession.getInstance().changeListName(name, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlacesList> delete(@PathVariable("id") String id) {
        Users usr = utils.getLoggedUser();
        PlacesList placesList = usr.getPlacesLists().stream().filter(x -> id.equals(x.getId())).findFirst().orElse(null);
        usr.getPlacesLists().remove(placesList);
        userRepository.save(usr);
        return  usr.getPlacesLists();
//        return UserSession.getInstance().deleteList(id);
    }

    @PostMapping("/{id}/add")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList add(@PathVariable("id") String id, @RequestBody Venue place) {
        List<Users> all = userRepository.findAll();
        Users user = all.stream().filter(x -> x.getPlacesLists().stream().anyMatch(y->y.getId().equals(id)))
                .findFirst().orElse(null);
        if(user != null){
            PlacesList placesList = user.getPlacesLists().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
            placesList.addPlace(place);

        }
        Users usr = utils.getLoggedUser();
        PlacesList placesList = usr.getPlacesLists().stream().filter(x -> id.equals(x.getId())).findFirst().orElse(null);
        placesList.addPlace(place);
        userRepository.save(usr);
        return UserSession.getInstance().addPlaceToList(place, id);
    }

    @PatchMapping("/{id}/checkin/{venueId}")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList checkin(@PathVariable("id") String listId, @PathVariable("venueId") String venueId) {
        return UserSession.getInstance().checkinPlaceInList(venueId, listId);
    }

}

