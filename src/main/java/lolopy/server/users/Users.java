package lolopy.server.users;

import java.util.List;

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
// import lolopy.server.profiles.Profiles;
// import lolopy.server.trips.Trips;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    private Long id;
    private String email;
    private String name;
    private String password;

    @ManyToMany
    @JoinTable(name = "user_trip", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private List<Long> tripIds;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Long profileId;

    public Users() {
    }

    public Users(Long id, String name, String email, String password, List<Long> tripIds, Long profileId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tripIds = tripIds;
        this.profileId = profileId;
    }

    public Users(String email, String name, String password) {
        this.email = email;
        this.name = name;
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
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getTrip() {
        return tripIds;
    }

    public void setTripIds(List<Long> tripIds) {
        this.tripIds = tripIds;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password + tripIds +
                '}';
    }
}
