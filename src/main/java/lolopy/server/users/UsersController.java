package lolopy.server.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lolopy.server.auth.CustomUserDetails;
import lolopy.server.auth.JwtService;
import lolopy.server.auth.MyUserDetailService;
import lolopy.server.auth.token.TokenService;
import lolopy.server.dtos.LoginForm;
import lolopy.server.dtos.GetLoginnedUserDTO;
import lolopy.server.dtos.GetUserDTO;
import lolopy.server.dtos.GetUsersTripsDTO;

@RestController
@RequestMapping(path = "api/v1/user")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService myUserDetailService;

    public UsersController(UsersService usersService, JwtService jwtService,
            AuthenticationManager authenticationManager, MyUserDetailService myUserDetailService,
            TokenService tokenService) {
        this.usersService = usersService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailService = myUserDetailService;
        this.tokenService = tokenService;
    }

    public class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    @GetMapping
    public ResponseEntity<List<GetUserDTO>> getUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Users> usersPage = usersService.getUsers(pageable);

        List<GetUserDTO> userDTOs = usersPage.getContent().stream()
                .map(user -> {
                    String profileName = user.getProfile() != null ? user.getProfile().getName() : null;
                    return new GetUserDTO(user.getId(), user.getEmail(), profileName, user.getRole().name());
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody Users user) {
        try {
            usersService.createUser(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User created");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAndGenerateToken(@RequestBody LoginForm loginForm) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.email(), loginForm.password()));

            if (authentication.isAuthenticated()) {
                Optional<Users> userOpt = usersService.getUserByEmail(loginForm.email());

                if (userOpt.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Користувача не знайдено");
                }

                Users user = userOpt.get();

                CustomUserDetails customUserDetails = new CustomUserDetails(user);

                tokenService.deleteExpiredAccessTokens(user.getName());
                tokenService.deleteExpiredRefreshTokens(user.getName());

                String accessToken = jwtService.generateToken(customUserDetails);
                String refreshToken = jwtService.generateRefreshToken(customUserDetails);

                tokenService.saveTokens(user, accessToken, refreshToken);

                GetLoginnedUserDTO userDTO = new GetLoginnedUserDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getRole(),
                        user.getProfile());
                Map<String, Object> response = new HashMap<>();
                response.put("access", accessToken);
                response.put("refresh", refreshToken);
                response.put("user", userDTO);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Авторизація не пройшла");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody(required = false) Map<String, String> requestBody) {

        String refreshToken = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            refreshToken = authHeader.substring(7);
        }

        if (refreshToken == null && requestBody != null) {
            refreshToken = requestBody.get("refreshToken");
        }

        if (refreshToken == null || !tokenService.isRefreshTokenValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing refresh token");
        }

        String username = jwtService.extractUserEmail(refreshToken);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token - no username found");
        }

        UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        tokenService.invalidateToken(refreshToken);

        tokenService.deleteExpiredRefreshTokens(username);

        String newAccessToken = jwtService.generateToken(customUserDetails);
        String newRefreshToken = jwtService.generateRefreshToken(customUserDetails);
        tokenService.saveTokens(customUserDetails.getUser(), newAccessToken, newRefreshToken);

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken,
                "tokenType", "Bearer",
                "expiresIn", 3600));
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            Optional<Users> userOptional = usersService.getUserById(userId);
            if (userOptional.isPresent()) {
                Users user = userOptional.get();

                GetUserDTO dto = new GetUserDTO(user.getId(), user.getEmail(), user.getName(), user.getRole().name(),
                        user.getProfile(), user.getTrips());

                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            Optional<Users> userOptional = usersService.getUserByEmail(email);
            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                GetUserDTO dto = new GetUserDTO(user.getId(), user.getEmail(), user.getName(), user.getRole().name(),
                        user.getProfile());

                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name) {
        try {
            Optional<Users> userOptional = usersService.getUserbyName(name);
            if (userOptional.isPresent()) {
                Users user = userOptional.get();

                GetUserDTO dto = new GetUserDTO(user.getId(), user.getEmail(), user.getName(), user.getRole().name(),
                        user.getProfile(), user.getTrips());

                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        try {
            Optional<Users> userOptional = usersService.updateUser(id, updatedUser);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();

                GetUserDTO dto = new GetUserDTO(user.getId(), user.getEmail(), user.getName(), user.getRole().name(),
                        user.getProfile(), user.getTrips());

                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            Optional<Users> user = usersService.getUserById(id);

            if (user.isPresent()) {
                boolean isDeleted = usersService.deleteUserById(id);

                if (isDeleted) {
                    return ResponseEntity.status(HttpStatus.OK).body("User deleted");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falied to delete user");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);

        }
    }

    @GetMapping("/trips/{id}")
    public ResponseEntity<?> getTripsByUserId(@PathVariable Long id) {
        try {
            Optional<Users> user = usersService.getUserById(id);

            if (user.isPresent()) {
                GetUsersTripsDTO response = new GetUsersTripsDTO(id, user.get().getTrips());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return null;
    }
}
