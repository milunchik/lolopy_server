package lolopy.server.users;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import lolopy.server.enums.Enums.Role;

@Configuration
public class UsersConfig {

    @Bean
    CommandLineRunner commandLineRunner(UsersRepository usersRepository) {
        return args -> {
            Optional<Users> existingUser = usersRepository.findUserByEmail("emiliia@gmail.com");
            if (existingUser.isEmpty()) {
                Users user = new Users("emiliia456879@gmail.com", "emiliia", "emiliiaemiliia456879@gmail.com",
                        Role.USER);

                // user.setPassword(passwordEncoder().encode("5a1c5523-bcfa-4158-8fbd-1b79c67139cb"));

                usersRepository.save(user);
            } else {
                System.out.println("User already exists, skipping insertion.");
            }
        };
    }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }
}
