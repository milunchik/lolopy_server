package lolopy.server.connects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Connect {

    @JsonProperty("user_id")
    Long user_id;

    @JsonProperty("trip_id")
    Long trip_id;
}
