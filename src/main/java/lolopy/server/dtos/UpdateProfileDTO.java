package lolopy.server.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;

public class UpdateProfileDTO {
    @JsonProperty("name")
    @Column()
    private String name;

    @JsonProperty("phone")
    @Column()
    private String phone;

    @JsonProperty("passport")
    @Column()
    private String passport;

    @JsonProperty("photo")
    @Column()
    private String photo;

    public UpdateProfileDTO() {
    }

    public UpdateProfileDTO(String name, String phone, String passport, String photo) {
        this.name = name;
        this.passport = passport;
        this.phone = phone;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
