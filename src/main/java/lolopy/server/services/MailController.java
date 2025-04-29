package lolopy.server.services;

import lolopy.server.trips.Trips;
import lolopy.server.users.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/booking-confirmation")
    public void sendBookingEmail(@RequestBody BookingRequest request) {
        emailService.sendBookConfirmationEmail(
                request.getTo(),
                request.getTrip(),
                request.getText());
    }

    @PostMapping("/signup-confirmation")
    public void postMethodName(@RequestBody ConfirmationRequest request) {
        emailService.sendSignConfirmationEmail(
                request.getTo(),
                request.getUser(),
                request.getText());
    }

    public static class BookingRequest {
        private String to;
        private Trips trip;
        private String text;

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public Trips getTrip() {
            return trip;
        }

        public void setTrip(Trips trip) {
            this.trip = trip;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class ConfirmationRequest {
        private String to;
        private Users user;
        private String text;

        public ConfirmationRequest(String to, Users user, String text) {
            this.user = user;
            this.text = text;
            this.to = to;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public Users getUser() {
            return user;
        }

        public void setUser(Users user) {
            this.user = user;
        }
    }
}
