package lolopy.server.profiles;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lolopy.server.trips.Trips;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping(path = "api/v1/profile")
public class ProfilesController {

    private final ProfilesService profilesService;

    public ProfilesController(ProfilesService profilesService) {
        this.profilesService = profilesService;
    }

    @GetMapping
    public List<Profiles> getProfiles() {
        return profilesService.getProfiles();
    }

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody Profiles profile) {
        try {
            profilesService.createProfile(profile);
            return ResponseEntity.status(HttpStatus.CREATED).body(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProfileById(@RequestParam Long id) {
        try {

            boolean isDeleted = profilesService.deleteProfile(id);

            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Profile deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update profile");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTrip(@PathVariable Long id, @RequestBody Profiles updatedProfile) {
        try {
            Optional<Profiles> profile = profilesService.getProfileById(id);

            if (profile.isPresent()) {
                profilesService.updateProfile(id, updatedProfile);
                return ResponseEntity.status(HttpStatus.OK).body(profile.get());

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfile(Long id) {
        try {

            boolean isDeleted = profilesService.deleteProfile(id);

            if (!isDeleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
            }

            return ResponseEntity.status(HttpStatus.OK).body("Profile deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
