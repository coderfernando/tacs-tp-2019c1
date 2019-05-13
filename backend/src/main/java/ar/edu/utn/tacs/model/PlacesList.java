package ar.edu.utn.tacs.model;

import ar.edu.utn.tacs.model.places.Venue;

import java.util.ArrayList;

public class PlacesList {

    private long id;
    private String name;
    private ArrayList<Venue> places;
    private ArrayList<String> visitedPlaces;

    public PlacesList() {
        this.places = new ArrayList<>();
        this.visitedPlaces = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public ArrayList<String> getVisitedPlaces() {
        return visitedPlaces;
    }

    public void setVisitedPlaces(ArrayList<String> visitedPlaces) {
        this.visitedPlaces = visitedPlaces;
    }

    public void addPlace(Venue place) {
        places.add(place);
    }

    public void checkin(String placeId) {
        visitedPlaces.add(placeId);
    }

}
