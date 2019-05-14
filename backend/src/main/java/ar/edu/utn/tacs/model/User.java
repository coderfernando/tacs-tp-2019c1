package ar.edu.utn.tacs.model;

import org.bson.types.ObjectId;

public class User {

    private ObjectId id;
    private String name;
    private String password;

    public User() {
        id = new ObjectId();
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() { return this.id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }

}
