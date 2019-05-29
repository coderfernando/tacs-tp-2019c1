package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.model.PlacesList;
import ar.edu.utn.tacs.model.UserSession;
import ar.edu.utn.tacs.model.places.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping ("api/me/lists")
public class ListsController {

    private Logger logger = LoggerFactory.getLogger(PlacesController.class);

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
        return UserSession.getInstance().addPlaceToList(place, id);
    }

    @PatchMapping("/{id}/checkin/{venueId}")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList checkin(@PathVariable("id") String listId, @PathVariable("venueId") String venueId) {
        return UserSession.getInstance().checkinPlaceInList(venueId, listId);
    }

}

