package lolopy.server.dtos;

import java.util.Set;

import lolopy.server.trips.Trips;

public class getUsersTripsDTO {
    private Long id;
    private Set<Trips> trips;

    public getUsersTripsDTO() {
    }

    public getUsersTripsDTO(Long id, Set<Trips> trips) {
        this.id = id;
        this.trips = trips;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Trips> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trips> trips) {
        this.trips = trips;
    }
}
