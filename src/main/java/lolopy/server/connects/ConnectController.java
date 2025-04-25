package lolopy.server.connects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lolopy.server.dtos.UserDTO;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "api/v1/book")
public class ConnectController {

    private final ConnectService connectService;

    public ConnectController(ConnectService connectService) {
        this.connectService = connectService;
    }

    @PatchMapping
    public ResponseEntity<?> connect(@RequestBody Connect connectingTripWithUser) {

        try {
            connectService.connectTripAndUser(connectingTripWithUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(connectingTripWithUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/disconnect-trip")
    public ResponseEntity<UserDTO> disconnectTripFromUser(@RequestBody Connect connect) {
        UserDTO updatedUser = connectService.disconnectTripAndUser(connect);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}