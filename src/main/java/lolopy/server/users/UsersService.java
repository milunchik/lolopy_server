package lolopy.server.users;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lolopy.server.auth.token.AccessTokenRepository;
import lolopy.server.auth.token.RefreshTokenRepository;
import lolopy.server.profiles.Profiles;
import lolopy.server.profiles.ProfilesRepository;
import lolopy.server.profiles.ProfilesService;

@Service
public class UsersService {

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final ProfilesRepository profilesRepository;
    private final UsersRepository usersRepository;

    private PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, ProfilesService profilesService,
            PasswordEncoder passwordEncoder, ProfilesRepository profilesRepository,
            RefreshTokenRepository refreshTokenRepository, AccessTokenRepository accessTokenRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.profilesRepository = profilesRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenRepository = accessTokenRepository;
    }

    public Page<Users> getUsers(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    public Users createUser(Users user) {
        Optional<Users> userByEmail = usersRepository.findUserByEmail(user.getEmail());

        if (userByEmail.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        Profiles profile = new Profiles(user.getName(), "Default Phone", "Default Passport");

        user.setProfile(profile);

        Users savedUser = usersRepository.save(user);

        profile.setUser(savedUser);

        profilesRepository.save(profile);

        return savedUser;
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

    public Optional<Users> getUserbyName(String name) {
        return usersRepository.findUserByName(name);
    }

    public Users getUserByProfileId(Long profileId) {
        return usersRepository.findUserByProfile(profileId)
                .orElseThrow(() -> new RuntimeException("User not found"));
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
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            if (updatedUser.getRole() != null) {
                existingUser.setRole(updatedUser.getRole());
            }

            usersRepository.save(existingUser);
            return Optional.of(existingUser);
        }

        return Optional.empty();
    }

    @Transactional
    public boolean deleteUserById(Long userId) {

        Optional<Users> optionalUser = usersRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            if (user.getAccess() != null) {
                user.getAccess().setUser(null);
                accessTokenRepository.save(user.getAccess());
                user.setAccess(null);
            }

            if (user.getRefresh() != null) {
                user.getRefresh().setUser(null);
                refreshTokenRepository.save(user.getRefresh());
                user.setRefresh(null);
            }

            if (user.getProfile() != null) {
                user.getProfile().setUser(null);
                user.setProfile(null);
            }

            usersRepository.delete(user);

            return true;
        }
        return false;
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<Users> user = usersRepository.findUserByEmail(email);
        if (user.isPresent()) {
            var userObj = user.get();

            return User.builder().username(userObj.getEmail()).password(userObj.getPassword())
                    .roles(userObj.getRole().name())
                    .build();
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
