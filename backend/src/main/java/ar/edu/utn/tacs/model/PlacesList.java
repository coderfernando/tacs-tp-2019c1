package ar.edu.utn.tacs.model;

import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.UUID;

public class PlacesList {

    private String id;
    private String name;
    private ArrayList<String> places;
    private ArrayList<String> visitedPlaces;

    public PlacesList() {
        id = UUID.randomUUID().toString();
        this.places = new ArrayList<>();
        this.visitedPlaces = new ArrayList<>();
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

    public ArrayList<String> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<String> places) {
        this.places = places;
    }

    public ArrayList<String> getVisitedPlaces() {
        return visitedPlaces;
    }

    public void setVisitedPlaces(ArrayList<String> visitedPlaces) {
        this.visitedPlaces = visitedPlaces;
    }

    public void addPlace(String place) {
        places.add(place);
    }

    public void checkin(String place) {
        visitedPlaces.add(place);
    }

}
