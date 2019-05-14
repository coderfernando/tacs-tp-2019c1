package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.model.PlacesList;
import ar.edu.utn.tacs.model.UserSession;
import ar.edu.utn.tacs.model.places.Venue;
import org.bson.types.ObjectId;
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
        return UserSession.getInstance().addList(new PlacesList(placesList.getName()));
    }

    @PatchMapping("/{id}/change-name")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList changeName(@PathVariable("id") ObjectId id, @RequestBody PlacesList placeList) {
        return UserSession.getInstance().changeListName(placeList.getName(), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlacesList> delete(@PathVariable("id") ObjectId id) {
        return UserSession.getInstance().deleteList(id);
    }

    @PostMapping("/{id}/add")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList add(@PathVariable("id") ObjectId id, @RequestBody String place) {
        return UserSession.getInstance().addPlaceToList(place, id);
    }

    @PatchMapping("/{id}/checkin")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList checkin(@PathVariable("id") ObjectId listid, @RequestBody Venue place) {
        return UserSession.getInstance().checkinPlaceInList(place.getId(), listid);
    }

}

