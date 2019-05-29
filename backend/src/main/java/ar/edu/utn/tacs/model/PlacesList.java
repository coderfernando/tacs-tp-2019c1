package ar.edu.utn.tacs.model;

import ar.edu.utn.tacs.model.places.Venue;
import java.util.ArrayList;
import java.util.UUID;

public class PlacesList {

    private String id;
    private String name;
    private ArrayList<Venue> places;
    private ArrayList<String> visitedPlacesIds;

    public PlacesList() {
        id = UUID.randomUUID().toString();
        this.places = new ArrayList<Venue>();
        this.visitedPlacesIds = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Venue> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Venue> places) {
        this.places = places;
    }

    public ArrayList<String> getVisitedPlacesIds() {
        return visitedPlacesIds;
    }

    public void setVisitedPlacesIds(ArrayList<String> visitedPlacesIds) {
        this.visitedPlacesIds = visitedPlacesIds;
    }

    public void addPlace(Venue place) {
        places.add(place);
    }

    public void checkin(String placeId) {
        visitedPlacesIds.add(placeId);
    }

}
