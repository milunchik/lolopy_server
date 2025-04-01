package lolopy.server.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lolopy.server.profiles.Profiles;
import lolopy.server.trips.Trips;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    private Long id;

    @JsonProperty("email")
    @Column(nullable = false)
    private String email;

    @JsonProperty("name")
    @Column(nullable = false)
    private String name;

    @JsonProperty("password")
    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profiles profile;

    @ManyToMany
    @JoinTable(name = "user_trip", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private List<Trips> trips;

    public Users() {
    }

    public Users(Long id, String name, String email, String password, List<Trips> trips, Profiles profile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.trips = trips;
        this.profile = profile;
    }

    public Users(String name, String email, String password, List<Trips> trips) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.trips = trips;
    }

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
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
        // if (this.profile != null) {
        // this.profile.setName(name);
        // }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Trips> getTripIds() {
        return trips;
    }

    public void setTripIds(List<Trips> trip) {
        this.trips = trip;
    }

    public Profiles getProfile() {
        return profile;
    }

    public void setProfile(Profiles profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password + trips +
                '}';
    }
}
