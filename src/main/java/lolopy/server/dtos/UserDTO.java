package lolopy.server.dtos;

import java.util.List;

public class UserDTO {
    private Long id;
    private String email;
    private List<TripDTO> trips;

    public UserDTO() {
    }

    public UserDTO(Long id, String email, List<TripDTO> trips) {
        this.id = id;
        this.email = email;
        this.trips = trips;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TripDTO> getTrips() {
        return trips;
    }

    public void setTrips(List<TripDTO> trips) {
        this.trips = trips;
    }
}
