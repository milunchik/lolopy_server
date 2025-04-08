// package lolopy.server.auth;

// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import lolopy.server.users.Users;
// import lolopy.server.users.UsersService;

// @Service
// public class AuthService {

// private final UsersService usersService;
// private final PasswordEncoder passwordEncoder;

// public AuthService(UsersService usersService, PasswordEncoder
// passwordEncoder) {
// this.usersService = usersService;
// this.passwordEncoder = passwordEncoder;
// }

// public boolean authenticateUser(String email, String password) {
// Users user = usersService.getUserByEmail(email).orElseThrow(() -> new
// RuntimeException("User not found"));

// return passwordEncoder.matches(password, user.getPassword());
// }
// }
