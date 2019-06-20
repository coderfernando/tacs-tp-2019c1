package ar.edu.utn.tacs.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Document(collection = "users")
public class Users {

    @Id
    private ObjectId id;
    private String name;
    private String password;
    private Boolean isAdmin;
    private Date lastAccess;
    private List<PlacesList> placesLists = new ArrayList<>();

    public Users(String name, String password, Boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Users() {
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

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public List<PlacesList> getPlacesLists() {
        return placesLists;
    }

    public void setPlacesLists(List<PlacesList> placesLists) {
        this.placesLists = placesLists;
    }


    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }
}
