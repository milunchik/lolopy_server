package lolopy.server.profiles;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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

    public Optional<Profiles> updateProfile(Long id, Profiles updatedProfile) {
        Optional<Profiles> profile = profilesRepository.findById(id);

        if (profile.isPresent()) {
            Profiles existingProfile = profile.get();

            if (updatedProfile.getName() != null) {
                existingProfile.setName(profile.get().getName());
            }
            if (updatedProfile.getPassport() != null) {
                existingProfile.setPassport(profile.get().getPassport());
            }
            if (updatedProfile.getClass() != null) {
                existingProfile.setPhone(profile.get().getPhone());
            }

            profilesRepository.save(existingProfile);
            return Optional.of(existingProfile);
        }
        return Optional.empty();
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
