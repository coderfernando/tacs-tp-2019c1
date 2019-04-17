package ar.utn.edu.tacs.controller;

import ar.utn.edu.tacs.model.places.ExplorePlacesResponse;
import ar.utn.edu.tacs.model.places.Item;
import ar.utn.edu.tacs.model.places.Venue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/places")
public class PlacesController {

    private String BASE_URL = "https://api.foursquare.com/v2/venues";
    private String apiVersion = "20180323";
    private String clientId = "L2O2R2SIVIE30PG1VVHU4H0OSCXF1ACUFW14CJF0KZRBBAUT";
    private String clientSecret = "NAMIH3VSGZ4XANKTRQLVDLKI2TBCW02Y15MH0F2FSUVLPRJ2";


    @GetMapping("")
    public ArrayList<Venue> getPlaces(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String lat,
            @RequestParam(required = false) String lon,
            @RequestParam String radius,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "false") Boolean open,
            @RequestParam(required = false) ArrayList<String> categories) {

        URI targetUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/explore")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("ll","40.7243,-74.0018") //TODO get user location
                .queryParam("radius",radius)
                .queryParam("v",apiVersion)
                .build()
                .encode()
                .toUri();

        ExplorePlacesResponse response = new RestTemplate().getForObject(targetUrl, ExplorePlacesResponse.class);
        if (response != null) {
            return response.getResponse()
                    .getGroups()
                    .stream()
                    .flatMap(group -> group.getItems().stream())
                    .distinct()
                    .map(Item::getVenue)
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No venues found with the given parameters");
        }

    }

}
