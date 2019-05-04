
package ar.utn.edu.tacs.model.places;

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
    "count",
    "lastCheckinExpiredAt",
    "marked",
    "unconfirmedCount"
})
public class BeenHere {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("lastCheckinExpiredAt")
    private Integer lastCheckinExpiredAt;
    @JsonProperty("marked")
    private Boolean marked;
    @JsonProperty("unconfirmedCount")
    private Integer unconfirmedCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("lastCheckinExpiredAt")
    public Integer getLastCheckinExpiredAt() {
        return lastCheckinExpiredAt;
    }

    @JsonProperty("lastCheckinExpiredAt")
    public void setLastCheckinExpiredAt(Integer lastCheckinExpiredAt) {
        this.lastCheckinExpiredAt = lastCheckinExpiredAt;
    }

    @JsonProperty("marked")
    public Boolean getMarked() {
        return marked;
    }

    @JsonProperty("marked")
    public void setMarked(Boolean marked) {
        this.marked = marked;
    }

    @JsonProperty("unconfirmedCount")
    public Integer getUnconfirmedCount() {
        return unconfirmedCount;
    }

    @JsonProperty("unconfirmedCount")
    public void setUnconfirmedCount(Integer unconfirmedCount) {
        this.unconfirmedCount = unconfirmedCount;
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
