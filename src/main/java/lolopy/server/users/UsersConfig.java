// package lolopy.server.users;

// import java.util.Optional;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class UsersConfig {

// // @Bean
// CommandLineRunner commandLineRunner(UsersRepository usersRepository) {

// return args -> {
// Optional<Users> existingUser =
// usersRepository.findUserByEmail("emiliia@gmail.com");
// if (existingUser.isEmpty()) {
// Users user = new Users("emiliia@gmail.com", "emiliia", "emiliia@gmail.com");
// usersRepository.save(user);
// } else {
// System.out.println("User already exists, skipping insertion.");
// }
// };
// }
// }
