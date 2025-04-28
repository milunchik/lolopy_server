package lolopy.server.trips;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lolopy.server.enums.Enums.Accommodation;
import lolopy.server.enums.Enums.FoodPlace;
import lolopy.server.enums.Enums.Transport;

public interface TripsRepositoryCustom {
    Page<Trips> findAllWithFilters(Pageable pageable, Transport transport, FoodPlace foodPlace,
            Accommodation accommodation, String country);
}
