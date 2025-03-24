package lolopy.server.trips;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TripsService {
    List<Long> userIds = Arrays.asList(6L, 1L);

    public List<Trips> getTrips() {
        return List.of(new Trips(1L, "country", "price", "category",
                "shortDescription", "longDescription", "capacity",
                "foodPlace", "transport", "accomodation", "date", userIds));
    }

}
