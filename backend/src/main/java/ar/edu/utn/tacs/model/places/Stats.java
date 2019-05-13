
package ar.edu.utn.tacs.model.places;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tipCount",
    "usersCount",
    "checkinsCount",
    "visitsCount"
})
public class Stats {

    @JsonProperty("tipCount")
    private Integer tipCount;
    @JsonProperty("usersCount")
    private Integer usersCount;
    @JsonProperty("checkinsCount")
    private Integer checkinsCount;
    @JsonProperty("visitsCount")
    private Integer visitsCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("tipCount")
    public Integer getTipCount() {
        return tipCount;
    }

    @JsonProperty("tipCount")
    public void setTipCount(Integer tipCount) {
        this.tipCount = tipCount;
    }

    @JsonProperty("usersCount")
    public Integer getUsersCount() {
        return usersCount;
    }

    @JsonProperty("usersCount")
    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    @JsonProperty("checkinsCount")
    public Integer getCheckinsCount() {
        return checkinsCount;
    }

    @JsonProperty("checkinsCount")
    public void setCheckinsCount(Integer checkinsCount) {
        this.checkinsCount = checkinsCount;
    }

    @JsonProperty("visitsCount")
    public Integer getVisitsCount() {
        return visitsCount;
    }

    @JsonProperty("visitsCount")
    public void setVisitsCount(Integer visitsCount) {
        this.visitsCount = visitsCount;
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
