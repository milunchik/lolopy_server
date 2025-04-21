package lolopy.server.trips;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "api/v1/trips")
public class TripsController {

    private final TripsService tripsService;

    public TripsController(TripsService tripsService) {
        this.tripsService = tripsService;
    }

    @GetMapping
    public List<Trips> getTrips() {
        return tripsService.geTrips();
    }

    @PostMapping
    public ResponseEntity<?> createTrip(@RequestBody Trips trip) {
        try {
            tripsService.createTrip(trip);
            return ResponseEntity.status(HttpStatus.CREATED).body(trip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTripById(@PathVariable Long id) {
        try {
            Optional<Trips> trip = tripsService.getTripById(id);
            if (trip.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(trip.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Trip not found");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTrip(@PathVariable Long id, @RequestBody Trips updateTrip) {
        try {
            Optional<Trips> trip = tripsService.getTripById(id);

            if (trip.isPresent()) {
                tripsService.updateTrips(id, updateTrip);
                return ResponseEntity.status(HttpStatus.OK).body(trip.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrip(@PathVariable Long id) {
        try {
            boolean isDeleted = tripsService.deleteTrip(id);

            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Trip deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
