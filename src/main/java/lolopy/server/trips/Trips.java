package lolopy.server.trips;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "trips")
public class Trips {
    @Id
    @SequenceGenerator(name = "trips_sequence", sequenceName = "trips_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trips_sequence")
    private Long id;
    private String country;
    private String price;
    private String category; // incoming/completed
    private String shortDescription;
    private String longDescription;
    private String capacity;
    private String foodPlace;
    private String transport;
    private String accommodation;
    private String date;

    @ManyToMany(mappedBy = "tripIds")
    private List<Long> user;

    public Trips() {
    }

    public Trips(Long id, String country, String price, String category, String shortDescription,
            String longDescription,
            String capacity, String foodPlace, String transport, String accommodation, String date,
            List<Long> user) {
        this.id = id;
        this.country = country;
        this.price = price;
        this.category = category;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.capacity = capacity;
        this.foodPlace = foodPlace;
        this.transport = transport;
        this.accommodation = accommodation;
        this.date = date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getFoodPlace() {
        return foodPlace;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public String getDate() {
        return date;
    }

    public String getTransport() {
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

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFoodPlace(String foodPlace) {
        this.foodPlace = foodPlace;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public List<Long> getUser() {
        return user;
    }

    public void setUser(List<Long> user) {
        this.user = user;
    }
}
