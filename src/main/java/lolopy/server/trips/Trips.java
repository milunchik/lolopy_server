package lolopy.server.trips;

import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lolopy.server.enums.Enums.Accommodation;
import lolopy.server.enums.Enums.FoodPlace;
import lolopy.server.enums.Enums.Transport;

@Entity
@Table(name = "trips")
public class Trips {
    @Id
    @SequenceGenerator(name = "trips_sequence", sequenceName = "trips_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trips_sequence")
    private Long id;

    @JsonProperty("country")
    @Column(nullable = false)
    private String country;

    @JsonProperty("city")
    @Column(nullable = false)
    private String city;

    @JsonProperty("price")
    @Column(nullable = false)
    private int price;

    @JsonProperty("longDescription")
    @Column(nullable = false)
    private String longDescription;

    @JsonProperty("capacity")
    @Column(nullable = false)
    private String capacity;

    @JsonProperty("foodPlace")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodPlace foodPlace;

    @JsonProperty("transport")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Transport transport;

    @JsonProperty("accommodation")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Accommodation accommodation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private String date;

    @JsonProperty("photo")
    @Column()
    private String photo;

    public Trips(Long id, String country, int price,
            String longDescription, String capacity, FoodPlace foodPlace, Transport transport,
            Accommodation accommodation, String date, String photos, String city) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.price = price;
        this.longDescription = longDescription;
        this.capacity = capacity;
        this.foodPlace = foodPlace;
        this.transport = transport;
        this.accommodation = accommodation;
        this.date = date;
        this.photo = photos;
    }

    public Trips(String country, int price,
            String longDescription, String capacity, FoodPlace foodPlace, Transport transport,
            Accommodation accommodation, String date, String photos, String city) {
        this.country = country;
        this.city = city;
        this.price = price;
        this.longDescription = longDescription;
        this.capacity = capacity;
        this.foodPlace = foodPlace;
        this.transport = transport;
        this.accommodation = accommodation;
        this.date = date;
        this.photo = photos;
    }

    public Trips() {
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public int getPrice() {
        return price;
    }

    public String getPhoto() {
        return photo;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public FoodPlace getFoodPlace() {
        return foodPlace;
    }

    public String getCapacity() {
        return capacity;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public String getDate() {
        return date;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFoodPlace(FoodPlace foodPlace) {
        this.foodPlace = foodPlace;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Trips))
            return false;
        Trips trip = (Trips) o;
        return id != null && id.equals(trip.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
