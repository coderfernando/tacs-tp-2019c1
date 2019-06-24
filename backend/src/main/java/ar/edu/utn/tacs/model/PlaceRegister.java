package ar.edu.utn.tacs.model;

import ar.edu.utn.tacs.model.places.Venue;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Document(collection = "placesregistered")
public class PlaceRegister {
    @Id
    private String id;

    private Venue venue;

    private Date registeredDate;

    @Transient
    private long usersInterestedCount;

    public PlaceRegister() {
    }

    public PlaceRegister(String id, Venue venue, Date registeredDate) {
        this.id = id;
        this.venue = venue;
        this.registeredDate = registeredDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public long getUsersInterestedCount() {
        return usersInterestedCount;
    }

    public void setUsersInterestedCount(long usersInterestedCount) {
        this.usersInterestedCount = usersInterestedCount;
    }

}
