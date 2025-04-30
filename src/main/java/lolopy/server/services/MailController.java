package lolopy.server.services;

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
                request.getCountry(),
                request.getCity(),
                request.getDate(),
                request.getCapacity(),
                request.getAccommodation(),
                request.getTransport());
    }

    @PostMapping("/signup-confirmation")
    public void postMethodName(@RequestBody ConfirmationRequest request) {
        emailService.sendSignConfirmationEmail(
                request.getTo());
    }

    public static class BookingRequest {
        private String to;
        private String country;
        private String city;
        private String date;
        private String capacity;
        private String accommodation;
        private String transport;

        public BookingRequest() {
        }

        public BookingRequest(String to, String country, String city, String date, String capacity,
                String accommodation, String transport) {
            this.to = to;
            this.country = country;
            this.city = city;
            this.date = date;
            this.capacity = capacity;
            this.accommodation = accommodation;
            this.transport = transport;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        public String getAccommodation() {
            return accommodation;
        }

        public void setAccommodation(String accommodation) {
            this.accommodation = accommodation;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTransport() {
            return transport;
        }

        public void setTransport(String transport) {
            this.transport = transport;
        }
    }

    public static class ConfirmationRequest {
        private String to;

        public ConfirmationRequest() {
        }

        public ConfirmationRequest(String to) {
            this.to = to;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

    }
}
