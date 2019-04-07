package ar.utn.edu.tacs.controller;

import ar.utn.edu.tacs.model.Place;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/places")
public class PlacesController {


    @GetMapping("")
    public ArrayList<Place> getPlaces(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String lat,
            @RequestParam(required = false) String lon,
            @RequestParam Integer radius,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "false") Boolean open,
            @RequestParam(required = false) ArrayList<String> categories) {


        Place place = new Place();
        place.setName("testplace");


        ArrayList<Place> places = new ArrayList<Place>();
        places.add(place);

        return places;
    }

}
