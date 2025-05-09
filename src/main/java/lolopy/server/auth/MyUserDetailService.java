package lolopy.server.auth;

import java.util.Optional;

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
            return new CustomUserDetails(userObj);
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
