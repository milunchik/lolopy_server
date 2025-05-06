package lolopy.server.profiles;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lolopy.server.dtos.UpdateProfileDTO;
import lolopy.server.users.Users;
import lolopy.server.users.UsersRepository;

@Service
public class ProfilesService {

    private final ProfilesRepository profilesRepository;
    private final UsersRepository usersRepository;

    public ProfilesService(ProfilesRepository profilesRepository, UsersRepository usersRepository) {
        this.profilesRepository = profilesRepository;
        this.usersRepository = usersRepository;
    }

    public List<Profiles> getProfiles() {
        return profilesRepository.findAll();
    }

    public Profiles createProfile(Profiles profile) {
        return profilesRepository.save(profile);
    }

    public Optional<Profiles> getProfileById(Long id) {
        return profilesRepository.findById(id);
    }

    public Optional<Profiles> getProfileByName(String name) {
        return profilesRepository.findProfileByName(name);
    }

    public Profiles updateProfile(Long id, UpdateProfileDTO updatedProfile) {
        Users user = usersRepository.findUserByProfile(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedProfile.getName() != null && !updatedProfile.getName().isEmpty()) {
            user.setName(updatedProfile.getName());
        }

        usersRepository.save(user);
        Profiles profile = profilesRepository.findById(id)
                .map(existingProfile -> {
                    if (updatedProfile.getName() != null && !updatedProfile.getName().isEmpty()) {
                        existingProfile.setName(updatedProfile.getName());
                    }
                    if (updatedProfile.getPassport() != null && !updatedProfile.getPassport().isEmpty()) {
                        existingProfile.setPassport(updatedProfile.getPassport());
                    }
                    if (updatedProfile.getPhone() != null && !updatedProfile.getPhone().isEmpty()) {
                        existingProfile.setPhone(updatedProfile.getPhone());
                    }
                    if (updatedProfile.getPhoto() != null && !updatedProfile.getPhoto().isEmpty()) {
                        existingProfile.setPhoto(updatedProfile.getPhoto());
                    }
                    return profilesRepository.save(existingProfile);
                })
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return profile;
    }

    public boolean deleteProfile(Long id) {
        Optional<Profiles> profile = profilesRepository.findById(id);

        if (profile.isPresent()) {
            profilesRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
