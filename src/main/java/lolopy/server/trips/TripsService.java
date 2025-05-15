package lolopy.server.trips;

import java.util.List;
import java.util.Optional;

import lolopy.server.enums.Enums.Accommodation;
import lolopy.server.enums.Enums.FoodPlace;
import lolopy.server.enums.Enums.Transport;
import lolopy.server.users.Users;
import lolopy.server.users.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TripsService {

    private final UsersRepository usersRepository;

    private final TripsRepository tripsRepository;

    public TripsService(TripsRepository tripsRepository, UsersRepository usersRepository) {
        this.tripsRepository = tripsRepository;
        this.usersRepository = usersRepository;
    }

    public Page<Trips> getTrips(Pageable pageable, List<Transport> transport, List<FoodPlace> foodPlace,
            List<Accommodation> accommodation, String country) {
        return tripsRepository.findAllWithFilters(pageable, transport, foodPlace, accommodation, country);
    }

    public Trips createTrip(Trips trip) {
        return tripsRepository.save(trip);
    }

    public Optional<Trips> getTripById(Long id) {
        return tripsRepository.findById(id);
    }

    public boolean deleteTrip(Long id) {
        Optional<Trips> tripOpt = tripsRepository.findById(id);

        if (tripOpt.isPresent()) {
            Trips trip = tripOpt.get();

            var users = usersRepository.findAll()
                    .stream()
                    .filter(user -> user.getTrips().contains(trip))
                    .toList();

            for (Users user : users) {
                user.getTrips().remove(trip);
                usersRepository.save(user);
            }

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

            if (updatedTripDetails.getCity() != null) {
                existingTrip.setCity(updatedTripDetails.getCity());
            }
            if (updatedTripDetails.getPrice() != 0) {
                existingTrip.setPrice(updatedTripDetails.getPrice());
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
            tripsRepository.save(existingTrip);
            return Optional.of(existingTrip);
        }

        return Optional.empty();
    }

}