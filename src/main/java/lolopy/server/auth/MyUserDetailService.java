package lolopy.server.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lolopy.server.users.Users;
import lolopy.server.users.UsersService;

@Service
public class MyUserDetailService implements UserDetailsService {

    public MyUserDetailRepository repository;
    private final UsersService usersService;

    public MyUserDetailService(UsersService usersService) {
        this.usersService = usersService;
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> user = usersService.getUserByEmail(email);
        if (user.isPresent()) {
            Users userObj = user.get();
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(userObj.getEmail())
                    .password(userObj.getPassword())
                    .roles(getRole(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(email);
        }
    }

    private String getRole(Users user) {
        return user.getRole().name();
    }

}
