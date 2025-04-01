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

    public Profiles createProfile(String name, String passport, String phone) {
        Profiles profile = new Profiles(name, passport, phone);
        return profilesRepository.save(profile);
    }

    public Optional<Profiles> getProfileById(Long id) {
        return profilesRepository.findById(id);
    }

}
