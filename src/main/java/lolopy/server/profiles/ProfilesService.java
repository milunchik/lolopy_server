package lolopy.server.profiles;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProfilesService {

    public List<Profiles> getProfiles() {
        return List.of(new Profiles(2L, "emiliia", "0123456789", "GRDHD456"));
    }

}
