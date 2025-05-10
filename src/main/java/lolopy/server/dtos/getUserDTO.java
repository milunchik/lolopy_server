package lolopy.server.dtos;

import java.util.Set;

import lolopy.server.profiles.Profiles;
import lolopy.server.trips.Trips;

public class GetUserDTO {
    private Long id;
    private String email;
    private String name;
    private String role;
    private Profiles profile;
    private Set<Trips> trips;

    public GetUserDTO() {
    }

    public GetUserDTO(Long id, String email, String name, String role, Profiles profile, Set<Trips> trips) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.profile = profile;
        this.trips = trips;
    }

    public GetUserDTO(Long id, String email, String name, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public GetUserDTO(Long id, String email, String name, String role, Profiles profile) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.profile = profile;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profiles getProfile() {
        return profile;
    }

    public void setProfile(Profiles profile) {
        this.profile = profile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Trips> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trips> trips) {
        this.trips = trips;
    }

}
