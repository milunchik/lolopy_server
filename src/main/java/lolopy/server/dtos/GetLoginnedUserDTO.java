package lolopy.server.dtos;

import lolopy.server.enums.Enums.Role;
import lolopy.server.profiles.Profiles;

public class GetLoginnedUserDTO {
    private Long id;
    private String email;
    private String name;
    private Role role;
    private Profiles profile;

    public GetLoginnedUserDTO() {
    }

    public GetLoginnedUserDTO(Long id, String email, String name, Role role, Profiles profile) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Profiles getProfile() {
        return profile;
    }

    public void setProfile(Profiles profile) {
        this.profile = profile;
    }
}
