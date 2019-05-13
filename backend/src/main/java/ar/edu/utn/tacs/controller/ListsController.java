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
    public List<PlacesList> getLists(@RequestBody PlacesList placesList) {
        return UserSession.getInstance().getLists();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PlacesList create(@RequestBody PlacesList placesList) {
        return UserSession.getInstance().addList(placesList);
    }

    @PatchMapping("/{id}/change-name")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList changeName(@PathVariable("id") long id, @RequestBody PlacesList placeList) {
        return UserSession.getInstance().changeListName(placeList.getName(), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlacesList> delete(@PathVariable("id") long id) {
        return UserSession.getInstance().deleteList(id);
    }

    @PostMapping("/{id}/add")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList add(@PathVariable("id") long id, @RequestBody Venue place) {
        return UserSession.getInstance().addPlaceToList(place, id);
    }

    @PatchMapping("/{id}/checkin")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList checkin(@PathVariable("id") long listid, @RequestBody Venue place) {
        return UserSession.getInstance().checkinPlaceInList(place.getId(), listid);
    }

}

