package ar.edu.utn.tacs.model.places;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "venues"
})
public class ResponseBySearch {

    @JsonProperty("venues")
    private ArrayList<Venue> venues = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("venues")
    public ArrayList<Venue> getVenues() {
        return venues;
    }

    @JsonProperty("items")
    public void setVenues(ArrayList<Venue> venues) {
        this.venues = venues;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
