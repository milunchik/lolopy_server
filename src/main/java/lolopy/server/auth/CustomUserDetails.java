package lolopy.server.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import lolopy.server.users.Users;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private Users user;

    public CustomUserDetails(Users user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getEmail(); 
    }

    public String getEmail() {
        return user.getEmail(); 
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
