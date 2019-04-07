package ar.utn.edu.tacs.model;

import java.util.ArrayList;

public class PlacesList {

    private long id;
    private String name;
    private ArrayList<Place> places;
    private ArrayList<Long> visitedPlaces;

    public PlacesList() { }

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

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public ArrayList<Long> getVisitedPlaces() {
        return visitedPlaces;
    }

    public void setVisitedPlaces(ArrayList<Long> visitedPlaces) {
        this.visitedPlaces = visitedPlaces;
    }

}
