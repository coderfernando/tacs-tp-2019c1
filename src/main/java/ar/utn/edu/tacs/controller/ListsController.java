package ar.utn.edu.tacs.controller;

import ar.utn.edu.tacs.model.PlacesList;
import ar.utn.edu.tacs.model.places.Venue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("me/lists")
public class ListsController {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PlacesList create(@RequestBody PlacesList placesList) {
        return placesList;
    }

    @PatchMapping("/{id}/change-name")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList changeName(@RequestBody String name) {
        PlacesList list = new PlacesList();
        return list;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable("id") long id) {
        return "List deleted";
    }

    @PostMapping("/{id}/add")
    @ResponseStatus(HttpStatus.OK)
    public String add(@PathVariable("id") long id, @RequestBody Venue place) {
        return "Place added";
    }

    @PatchMapping("/{id}/checkin")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList checkin(@PathVariable("id") long id, @RequestBody long placeId) {
        PlacesList list = new PlacesList();
        return list;
    }

}
