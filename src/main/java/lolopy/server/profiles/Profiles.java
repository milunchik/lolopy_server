package lolopy.server.profiles;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lolopy.server.users.Users;

@Entity
@Table(name = "profiles")
public class Profiles {
    @Id
    @SequenceGenerator(name = "profiles_sequence", sequenceName = "profiles_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profiles_sequence")
    private Long id;
    private String name;
    private String phone;
    private String passport;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public Profiles() {
    }

    public Profiles(Long id, String name, String phone, String passport, Users userId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.passport = passport;
        this.user = userId;
    }

    public Profiles(String name, String phone, String passport) {
        this.name = name;
        this.phone = phone;
        this.passport = passport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Users getUserId() {
        return user;
    }

    public void setUserId(Users userId) {
        this.user = userId;
    }

}