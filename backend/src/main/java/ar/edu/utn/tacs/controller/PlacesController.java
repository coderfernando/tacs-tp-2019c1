package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.exceptions.MissingParametersException;
import ar.edu.utn.tacs.exceptions.VenuesNotFoundException;
import ar.edu.utn.tacs.model.places.*;
import ar.edu.utn.tacs.repositories.VenuesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("api/places")
public class PlacesController {


    @Autowired
    VenuesRepository venuesRepository;

    private String BASE_URL = "https://api.foursquare.com/v2/venues";
    private String apiVersion = "20180323";
    private String clientId = "L2O2R2SIVIE30PG1VVHU4H0OSCXF1ACUFW14CJF0KZRBBAUT";
    private String clientSecret = "NAMIH3VSGZ4XANKTRQLVDLKI2TBCW02Y15MH0F2FSUVLPRJ2";
    private Logger logger = LoggerFactory.getLogger(PlacesController.class);

    @GetMapping("")
    public ArrayList<Venue> getPlaces(
            @RequestParam(required = false) String near,
            @RequestParam(required = false) String lat,
            @RequestParam(required = false) String lon,
            @RequestParam(required = false) String radius,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "false") Boolean open,
            @RequestParam(required = false) String section) throws VenuesNotFoundException, MissingParametersException {

        ArrayList<Venue> venues  = new ArrayList<Venue>();

        if (radius == null) {
            throw new MissingParametersException(Collections.singletonList("radius"));
        } else if (lat == null && lon == null && near == null) {
            throw new MissingParametersException(Arrays.asList("latitude", "longitude"));
        } else if (lat == null && near == null) {
            throw new MissingParametersException(Collections.singletonList("latitude"));
        } else if (lon == null && near == null) {
            throw new MissingParametersException(Collections.singletonList("longitude"));
        }

        URI targetUrl = getUriForVenuesExplore(near, lat, lon, radius, limit, page, open, section);

        try {
            logger.info("GET -> " + targetUrl.getQuery());
            ExplorePlacesResponse response = new RestTemplate().getForObject(targetUrl, ExplorePlacesResponse.class);
            if (response != null && response.getMeta().getCode() == 200) {
                venues = response.getResponse()
                        .getGroups()
                        .stream()
                        .flatMap(group -> group.getItems().stream())
                        .distinct()
                        .map(Item::getVenue)
                        .collect(Collectors.toCollection(ArrayList::new));
            } else {
                if (response != null) {
                    throw new VenuesNotFoundException(response.getMeta().getErrorDetail());
                } else {
                    throw new VenuesNotFoundException();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new VenuesNotFoundException();
        }

        return venues;
    }

    @GetMapping("/{id}")
    public Venue getPlaceById(@PathVariable String id) throws VenuesNotFoundException, MissingParametersException {

        Venue venue  = new Venue();

        if (id == null){
            throw new MissingParametersException(Collections.singletonList("id"));
        }

        URI targetUrl = getUriForVenueId(id);

        try {
            logger.info("GET -> " + targetUrl.getQuery());
            VenueByIdResponse response = new RestTemplate().getForObject(targetUrl, VenueByIdResponse.class);
            if (response != null && response.getMeta().getCode() == 200) {
                venue = response.getResponse().getVenue();
            } else {
                if (response != null) {
                    throw new VenuesNotFoundException(response.getMeta().getErrorDetail());
                } else {
                    throw new VenuesNotFoundException();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new VenuesNotFoundException();
        }

        return venue;
    }

    @GetMapping("/search")
    public ArrayList<Venue> getPlacesBySearch(
        @RequestParam(required = false) String termino,
        @RequestParam(required = false) String near,
        @RequestParam(required = false) String lat,
        @RequestParam(required = false) String lon,
        @RequestParam(required = false) String radius)
        throws VenuesNotFoundException, MissingParametersException {

        ArrayList<Venue> venues = new ArrayList<Venue>();

        if (termino == null){
            throw new MissingParametersException(Collections.singletonList("termino"));
        }

        URI targetUrl = getUriForVenuesSearch(termino, near, lat, lon, radius);

        try {
            logger.info("GET -> " + targetUrl.getQuery());
            VenuesBySearchResponse response = new RestTemplate().getForObject(targetUrl, VenuesBySearchResponse.class);
            if (response != null && response.getMeta().getCode() == 200) {
                venues = response.getResponse()
                        .getVenues();
            } else {
                if (response != null) {
                    throw new VenuesNotFoundException(response.getMeta().getErrorDetail());
                } else {
                    throw new VenuesNotFoundException();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new VenuesNotFoundException();
        }

        return venues;
    }

    private URI getUriForVenuesExplore(String near, String lat, String lon, String radius, Integer limit, Integer page, Boolean open, String section) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/explore")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("radius", radius)
                .queryParam("v", apiVersion);

        if (lat != null && lon != null) {
            builder.queryParam("ll", lat + "," + lon);
        }

        if (near != null) {
            builder.queryParam("near", near + ", AR");
        }

        if (open != null) {
            builder.queryParam("openNow", open);
        }

        if (section != null) {
            builder.queryParam("section", section);
        }
        return builder.build().encode().toUri();
    }

    private URI getUriForVenueId(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/".concat(id))
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("v", apiVersion);

        return builder.build().encode().toUri();
    }

    private URI getUriForVenuesSearch(String termino, String near, String lat, String lon, String radius) {
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/search")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("v", apiVersion)
                .queryParam("radius", radius);

        if (termino != null) {
            builder.queryParam("query", termino);
        }

        if (lat != null && lon != null) {
            builder.queryParam("ll", lat + "," + lon);
        }

        if (near != null) {
            builder.queryParam("near", near + ", AR");
        }

        return builder.build().encode().toUri();
    }

}
