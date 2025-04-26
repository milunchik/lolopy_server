package lolopy.server.profiles;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lolopy.server.dtos.UpdateProfileDTO;

@Service
public class ProfilesService {

    private final ProfilesRepository profilesRepository;

    public ProfilesService(ProfilesRepository profilesRepository) {
        this.profilesRepository = profilesRepository;
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
        return profilesRepository.findById(id)
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
                    return profilesRepository.save(existingProfile);
                })
                .orElseThrow(() -> new RuntimeException("Profile not found"));
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
