// package lolopy.server.auth;

// import java.util.Optional;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import
// org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Component;

// import lolopy.server.users.Users;
// import lolopy.server.users.UsersRepository;

// @Component
// class DatabaseUserDetails implements UserDetailsService {

// private final UsersRepository userRepository;

// DatabaseUserDetails(UsersRepository userRepository) {
// this.userRepository = userRepository;
// }

// @Override
// public UserDetails loadUserByUsername(String email) throws
// UsernameNotFoundException {

// Optional<Users> userFromDatabase = userRepository.findUserByEmail(email);

// if (userFromDatabase == null) {
// throw new UsernameNotFoundException(email);
// }

// return org.springframework.security.core.userdetails.User
// .withUsername(userFromDatabase.get().getName())
// .password(userFromDatabase.get().getPassword())
// .roles(userFromDatabase.get().getRole().name())
// .build();
// }
// }