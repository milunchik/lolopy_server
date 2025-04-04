package lolopy.server.trips;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TripsService {

    private final TripsRepository tripsRepository;

    public TripsService(TripsRepository tripsRepository) {
        this.tripsRepository = tripsRepository;
    }

    public List<Trips> geTrips() {
        return tripsRepository.findAll();
    }

    public Trips createTrip(Trips trip) {
        return tripsRepository.save(trip);
    }

    public Optional<Trips> getTripById(Long id) {
        return tripsRepository.findById(id);
    }

    public boolean deleteTrip(Long id) {
        Optional<Trips> trip = tripsRepository.findById(id);

        if (trip.isPresent()) {
            tripsRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }

    public Optional<String> getTripsPhoto(Long id) {
        Optional<Trips> trip = tripsRepository.findById(id);

        return trip.flatMap(t -> Optional.ofNullable(t.getPhoto()));
    }

    public Optional<Trips> updateTrips(Long id, Trips updatedTripDetails) {
        Optional<Trips> optionalTrip = tripsRepository.findById(id);

        if (optionalTrip.isPresent()) {
            Trips existingTrip = optionalTrip.get();

            if (updatedTripDetails.getCountry() != null) {
                existingTrip.setCountry(updatedTripDetails.getCountry());
            }
            if (updatedTripDetails.getPrice() != 0) {
                existingTrip.setPrice(updatedTripDetails.getPrice());
            }
            if (updatedTripDetails.getShortDescription() != null) {
                existingTrip.setShortDescription(updatedTripDetails.getShortDescription());
            }
            if (updatedTripDetails.getLongDescription() != null) {
                existingTrip.setLongDescription(updatedTripDetails.getLongDescription());
            }
            if (updatedTripDetails.getCapacity() != null) {
                existingTrip.setCapacity(updatedTripDetails.getCapacity());
            }
            if (updatedTripDetails.getFoodPlace() != null) {
                existingTrip.setFoodPlace(updatedTripDetails.getFoodPlace());
            }
            if (updatedTripDetails.getTransport() != null) {
                existingTrip.setTransport(updatedTripDetails.getTransport());
            }
            if (updatedTripDetails.getAccommodation() != null) {
                existingTrip.setAccommodation(updatedTripDetails.getAccommodation());
            }
            if (updatedTripDetails.getDate() != null) {
                existingTrip.setDate(updatedTripDetails.getDate());
            }
            if (updatedTripDetails.getPhoto() != null) {
                existingTrip.setPhoto(updatedTripDetails.getPhoto());
            }
            if (updatedTripDetails.getUser() != null) {
                existingTrip.setUser(updatedTripDetails.getUser());
            }

            tripsRepository.save(existingTrip);
            return Optional.of(existingTrip);
        }

        return Optional.empty();
    }

}