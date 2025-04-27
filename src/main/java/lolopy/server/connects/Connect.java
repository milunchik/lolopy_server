package lolopy.server.connects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Connect {

    @JsonProperty("user_id")
    Long user_id;

    @JsonProperty("trip_id")
    Long trip_id;

    public Long getTrip_id() {
        return trip_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setTrip_id(Long trip_id) {
        this.trip_id = trip_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
