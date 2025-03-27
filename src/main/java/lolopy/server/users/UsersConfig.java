package lolopy.server.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersConfig {

    @Bean
    CommandLineRunner commandLineRunner(UsersRepository usersRepository) {

        return args -> {
            Users user = new Users("emiliia", "emiliia@gmail.com",
                    "emiliia@gmail.com");

            usersRepository.save(user);
        };
    }
}
