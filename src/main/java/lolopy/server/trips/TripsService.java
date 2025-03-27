package lolopy.server.trips;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TripsService {

    public List<Trips> getTrips() {
        return List.of(new Trips("country", "price", "category",
                "shortDescription", "longDescription", "capacity",
                "foodPlace", "transport", "accomodation", "date"));
    }

}
