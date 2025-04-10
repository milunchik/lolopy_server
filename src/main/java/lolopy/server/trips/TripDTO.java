package lolopy.server.trips;

public class TripDTO {
    private Long id;
    private String date;

    public TripDTO() {
    }

    public TripDTO(Long id, String date) {
        this.id = id;
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

}
