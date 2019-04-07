package ar.utn.edu.tacs.controller;

import ar.utn.edu.tacs.model.PlacesList;
import ar.utn.edu.tacs.request.AddPlaceToListRequest;
import ar.utn.edu.tacs.request.ChangePlacesListNameRequest;
import ar.utn.edu.tacs.request.CheckinPlaceInPlacesListRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/placeslists")
public class ListsController {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PlacesList create(@RequestBody PlacesList placesList) {
        return placesList;
    }

    @PostMapping("/change-name")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList changeName(@RequestBody ChangePlacesListNameRequest request) {
        PlacesList list = new PlacesList();
        return list;
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@RequestBody Long id) {
        return "List deleted";
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public String add(@RequestBody AddPlaceToListRequest placesList) {
        return "Place added";
    }

    @PostMapping("/checkin")
    @ResponseStatus(HttpStatus.OK)
    public PlacesList checkin(@RequestBody CheckinPlaceInPlacesListRequest request) {
        PlacesList list = new PlacesList();
        return list;
    }

}
