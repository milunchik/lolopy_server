package lolopy.server.trips;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lolopy.server.enums.Enums.Accommodation;
import lolopy.server.enums.Enums.FoodPlace;
import lolopy.server.enums.Enums.Transport;

public interface TripsRepositoryCustom {
    Page<Trips> findAllWithFilters(Pageable pageable, List<Transport> transport, List<FoodPlace> foodPlace,
            List<Accommodation> accommodation, String country);
}
