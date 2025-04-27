package lolopy.server.trips;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "api/v1/trips")
public class TripsController {

    private final TripsRepository tripsRepository;

    private final TripsService tripsService;
    private static final String uploadDir = "C:/Users/Mila/Desktop/lolopy/server/";

    public TripsController(TripsService tripsService, TripsRepository tripsRepository) {
        this.tripsService = tripsService;
        this.tripsRepository = tripsRepository;
    }

    @GetMapping
    public ResponseEntity<List<Trips>> getTrips(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Trips> tripsPage = tripsService.getTrips(pageable);

        return ResponseEntity.ok(tripsPage.getContent());
    }

    @PostMapping
    public ResponseEntity<?> createTrip(@RequestBody Trips trip) {
        try {
            tripsService.createTrip(trip);
            return ResponseEntity.status(HttpStatus.CREATED).body(trip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTripById(@PathVariable Long id) {
        try {
            Optional<Trips> trip = tripsService.getTripById(id);
            if (trip.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(trip.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Trip not found");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTrip(@PathVariable Long id, @RequestBody Trips updateTrip) {
        try {
            Optional<Trips> trip = tripsService.getTripById(id);

            if (trip.isPresent()) {
                tripsService.updateTrips(id, updateTrip);
                return ResponseEntity.status(HttpStatus.OK).body(trip.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/photo/{tripId}")
    public ResponseEntity<Resource> getProfilePhoto(@PathVariable Long tripId) {
        Trips trip = tripsService.getTripById(tripId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found"));
        String photoPath = trip.getPhoto();
        Path filePath = Paths.get(uploadDir, photoPath);

        if (Files.exists(filePath)) {
            Resource resource = new FileSystemResource(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/upload/{tripId}")
    public ResponseEntity<Map<String, String>> uploadPhoto(@RequestParam("file") MultipartFile file,
            @PathVariable("tripId") Long tripId) {
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
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            String photoPath = "/uploads/avatars/" + fileName;

            Trips trip = tripsService.getTripById(tripId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            trip.setPhoto(photoPath);

            tripsRepository.save(trip);

            Map<String, String> response = new HashMap<>();
            response.put("photoPath", photoPath);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error while uploading the file"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrip(@PathVariable Long id) {
        try {
            boolean isDeleted = tripsService.deleteTrip(id);

            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Trip deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
