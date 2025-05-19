package lolopy.server.profiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import lolopy.server.dtos.UpdateProfileDTO;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping(path = "api/v1/profile")
public class ProfilesController {

    private final ProfilesRepository profilesRepository;

    private final ProfilesService profilesService;
    private static final String uploadDir = "C:/Users/Mila/Desktop/projects/lolopy/server/";

    public ProfilesController(ProfilesService profilesService,
            ProfilesRepository profilesRepository) {
        this.profilesService = profilesService;
        this.profilesRepository = profilesRepository;
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
    public ResponseEntity<?> updateTrip(@PathVariable Long id, @RequestBody UpdateProfileDTO updatedProfile) {
        try {
            Profiles updated = profilesService.updateProfile(id, updatedProfile);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/photo/{profileId}")
    public ResponseEntity<Resource> getProfilePhoto(@PathVariable Long profileId) {
        Profiles profile = profilesService.getProfileById(profileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));
        String photoPath = profile.getPhoto();
        if (photoPath.startsWith("/")) {
            photoPath = photoPath.substring(1);
        }

        Path filePath = Paths.get(uploadDir, photoPath).toAbsolutePath();
        System.out.println("Looking for photo at: " + filePath);

        if (Files.exists(filePath)) {
            Resource resource = new FileSystemResource(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/upload/{profileId}")
    public ResponseEntity<Map<String, String>> uploadPhoto(@RequestParam("file") MultipartFile file,
            @PathVariable("profileId") Long profileId) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No file provided"));
        }

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid file type. Only image files are allowed."));
        }

        long maxSize = 5 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "File size exceeds the limit of 5MB"));
        }

        try {
            String uploadDir = "uploads/avatars/";
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            String fileName = UUID.randomUUID().toString() + fileExtension;
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            String photoPath = "/uploads/avatars/" + fileName;

            Profiles profile = profilesService.getProfileById(profileId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            profile.setPhoto(photoPath);

            profilesRepository.save(profile);

            Map<String, String> response = new HashMap<>();
            response.put("photoPath", photoPath);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error while uploading the file"));
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
