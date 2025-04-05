package lolopy.server.users;

import java.util.List;
import java.util.Optional;

//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lolopy.server.profiles.Profiles;
import lolopy.server.profiles.ProfilesService;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final ProfilesService profilesService;

    // private PasswordEncoder passwordEncoder;

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
        Profiles profile = new Profiles(user.getName(), "Default Passport", "Default Phone");

        Profiles newProfile = profilesService.createProfile(profile);
        user.setProfile(newProfile);

        // String hashedPassword = passwordEncoder.encode(user.getPassword());
        // System.out.println(hashedPassword);
        // user.setPassword(hashedPassword);

        usersRepository.save(user);

        return user;
    }

    public Users getUser(Users user) {
        Optional<Users> userByEmail = usersRepository.findUserByEmail(user.getEmail());

        if (userByEmail.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        return userByEmail.get();
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findUserByEmail(email);
    }

    public Optional<Users> updateUser(Long id, Users updatedUser) {
        Optional<Users> userOptional = usersRepository.findById(id);

        if (userOptional.isPresent()) {
            Users existingUser = userOptional.get();

            if (updatedUser.getName() != null) {
                existingUser.setName(updatedUser.getName());

                if (existingUser.getProfile() != null) {
                    existingUser.getProfile().setName(updatedUser.getName());
                }
            }

            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(updatedUser.getPassword());
            }

            if (updatedUser.getRole() != null) {
                existingUser.setRole(updatedUser.getRole());
            }

            if (updatedUser.getPhoto() != null) {
                existingUser.setPhoto(updatedUser.getPhoto());
            }

            usersRepository.save(existingUser);
            return Optional.of(existingUser);
        }

        return Optional.empty();
    }

    public boolean deleteById(Long id) {
        Optional<Users> user = usersRepository.findById(id);

        if (user.isPresent()) {
            usersRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
