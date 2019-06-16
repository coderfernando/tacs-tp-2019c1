package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.model.PlacesList;
import ar.edu.utn.tacs.model.UserSession;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.model.places.Venue;
import ar.edu.utn.tacs.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping ("api/me/lists")
public class ListsController {

    private Logger logger = LoggerFactory.getLogger(PlacesController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<PlacesList> getLists() {
        return UserSession.getInstance().getLists();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PlacesList create(@RequestBody PlacesList placesList) {
        logger.info("Request to create List name:" + placesList.getName());
        return UserSession.getInstance().addList(placesList);
    }

    @PatchMapping("/{id}/change-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList changeName(@PathVariable("id") String id, @PathVariable("name") String name) {
        return UserSession.getInstance().changeListName(name, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlacesList> delete(@PathVariable("id") String id) {
        return UserSession.getInstance().deleteList(id);
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
        return UserSession.getInstance().addPlaceToList(place, id);
    }

    @PatchMapping("/{id}/checkin/{venueId}")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList checkin(@PathVariable("id") String listId, @PathVariable("venueId") String venueId) {
        return UserSession.getInstance().checkinPlaceInList(venueId, listId);
    }

}

