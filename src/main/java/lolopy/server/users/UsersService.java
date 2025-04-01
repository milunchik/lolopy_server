package lolopy.server.users;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lolopy.server.profiles.Profiles;
import lolopy.server.profiles.ProfilesService;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final ProfilesService profilesService;

    public UsersService(UsersRepository usersRepository, ProfilesService profilesService) {
        this.usersRepository = usersRepository;
        this.profilesService = profilesService;
    }

    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    public Users createUser(Users user) {
        Optional<Users> userByEmail = usersRepository.findUserByEmail(user.getEmail());

        if (userByEmail.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Profiles profile = profilesService.createProfile(user.getName(), "Default Passport", "Default Phone");
        user.setProfile(profile);

        usersRepository.save(user);

        return user;
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findUserByEmail(email);
    }
}
