package ar.utn.edu.tacs.controller;

import ar.utn.edu.tacs.model.Place;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/places")
public class PlacesController {

    @GetMapping("")
    public ArrayList<Place> getPlacesByCategories(@RequestParam ArrayList<String> categories) {

        Place place = new Place();
        place.setName("testplace");
        place.setCategories(categories);

        ArrayList<Place> places = new ArrayList<Place>();
        places.add(place);

        return places;
    }

}
