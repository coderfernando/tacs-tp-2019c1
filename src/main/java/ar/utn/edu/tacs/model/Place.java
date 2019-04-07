package ar.utn.edu.tacs.model;

import java.util.ArrayList;

public class Place {

    private long id;
    private String name;
    private ArrayList<String> categories;

    public Place() { }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public ArrayList<String> getCategories() { return categories; }

    public void setCategories(ArrayList<String> categories) { this.categories = categories; }
}
