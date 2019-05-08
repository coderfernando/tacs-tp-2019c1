package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.model.PlacesList;
import ar.edu.utn.tacs.model.UserSession;
import ar.edu.utn.tacs.model.places.Venue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping ("api/me/lists")
public class ListsController {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSession create(@RequestBody PlacesList placesList) {
        UserSession.getInstance().addList(placesList);
        return UserSession.getInstance();
    }

    @PatchMapping("/{id}/change-name")
    @ResponseStatus(HttpStatus.OK)
    public UserSession changeName(@PathVariable("id") long id, @RequestBody String name) {
        UserSession.getInstance().changeListName(name, id);
        return UserSession.getInstance();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserSession delete(@PathVariable("id") long id) {
        UserSession.getInstance().deleteList(id);
        return UserSession.getInstance();
    }

    @PostMapping("/{id}/add")
    @ResponseStatus(HttpStatus.OK)
    public UserSession add(@PathVariable("id") long id, @RequestBody Venue place) {
        UserSession.getInstance().addPlaceToList(place, id);
        return UserSession.getInstance();
    }

    @PatchMapping("/{id}/checkin")
    @ResponseStatus(HttpStatus.OK)
    public UserSession checkin(@PathVariable("id") long id, @RequestBody String placeId) {
        UserSession.getInstance().checkinPlaceInList(placeId, id);
        return UserSession.getInstance();
    }

}
