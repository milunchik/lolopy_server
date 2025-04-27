package lolopy.server.connects;

import lolopy.server.dtos.TripDTO;
import lolopy.server.dtos.UserDTO;
import lolopy.server.trips.*;
import lolopy.server.users.*;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ConnectService {

    private final UsersRepository usersRepository;
    private final TripsRepository tripsRepository;

    public ConnectService(UsersRepository usersRepository, TripsRepository tripsRepository) {
        this.usersRepository = usersRepository;
        this.tripsRepository = tripsRepository;
    }

    public UserDTO connectTripAndUser(Connect connectingTripWithUser) {
        Optional<Users> optUser = usersRepository.findById(connectingTripWithUser.user_id);
        Optional<Trips> optTrip = tripsRepository.findById(connectingTripWithUser.trip_id);

        if (optUser.isPresent() && optTrip.isPresent()) {
            Users user = optUser.get();
            Trips trip = optTrip.get();

            if (!user.getTrips().contains(trip)) {
                user.getTrips().add(trip);
            }

            Users savedUser = usersRepository.save(user);

            UserDTO dto = new UserDTO();
            dto.setId(savedUser.getId());
            dto.setEmail(savedUser.getEmail());
            dto.setTrips(savedUser.getTrips().stream()
                    .map(t -> new TripDTO(t.getId(), t.getDate()))
                    .collect(Collectors.toList()));

            return dto;
        }

        return null;
    }

    public boolean existsByTripIdAndUserId(Long tripId, Long userId) {
        return usersRepository.existsByTripIdAndUserId(tripId, userId);
    }

    public UserDTO disconnectTripAndUser(Connect disconnectingTripWithUser) {
        Optional<Users> optUser = usersRepository.findById(disconnectingTripWithUser.user_id);
        Optional<Trips> optTrip = tripsRepository.findById(disconnectingTripWithUser.trip_id);

        if (optUser.isPresent() && optTrip.isPresent()) {
            Users user = optUser.get();
            Trips trip = optTrip.get();

            if (user.getTrips().contains(trip)) {
                user.getTrips().remove(trip);
            }

            Users savedUser = usersRepository.save(user);

            UserDTO dto = new UserDTO();
            dto.setId(savedUser.getId());
            dto.setEmail(savedUser.getEmail());
            dto.setTrips(savedUser.getTrips().stream()
                    .map(t -> new TripDTO(t.getId(), t.getDate()))
                    .collect(Collectors.toList()));

            return dto;
        }

        return null;
    }

}
