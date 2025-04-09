package lolopy.server.trips;

public class TripDTO {
    private Long id;
    private String country;
    private String date;

    public TripDTO(Long id, String date, String country) {
        this.id = id;
        this.country = country;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
