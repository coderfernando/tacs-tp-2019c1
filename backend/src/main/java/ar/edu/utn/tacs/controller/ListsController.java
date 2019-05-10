package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.model.PlacesList;
import ar.edu.utn.tacs.model.UserSession;
import ar.edu.utn.tacs.model.places.Venue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping ("api/me/lists")
public class ListsController {

    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public List<PlacesList> all() {
        return UserSession.getInstance().getLists();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSession create(@RequestBody String name) {
        PlacesList list = new PlacesList();
        list.setName(name);
        list.setId(1);
        list.setPlaces(new ArrayList<Venue>());
        list.setVisitedPlaces(new ArrayList<String>());
        UserSession.getInstance().addList(list);
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
