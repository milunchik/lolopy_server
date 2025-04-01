package lolopy.server.trips;

import java.util.List;
import org.springframework.stereotype.Service;
import lolopy.server.enums.Enums.Accommodation;
import lolopy.server.enums.Enums.Category;
import lolopy.server.enums.Enums.FoodPlace;
import lolopy.server.enums.Enums.Transport;

@Service
public class TripsService {

    public List<Trips> getTrips() {
        return List.of(new Trips(
                "France", 120, Category.INCOMING,
                "Short description", "Long description", "2",
                FoodPlace.CAFE, Transport.PLANE, Accommodation.HOTEL,
                "2025-08-15"));
    }
}
