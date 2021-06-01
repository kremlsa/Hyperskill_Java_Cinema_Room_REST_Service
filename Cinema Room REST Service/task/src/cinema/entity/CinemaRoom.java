package cinema.entity;



import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.ArrayList;
import java.util.List;

public class CinemaRoom {

    private Long rows;
    private Long columns;
    private List<Seat> availableSeats;

    public CinemaRoom(Long rows, Long columns) {
        this.rows = rows;
        this.columns = columns;
        this.availableSeats = setAvailableSeats();

    }

    public CinemaRoom() {
    }

    public List<Seat> setAvailableSeats() {
        availableSeats = new ArrayList<>();
        for (long j = 1L; j <= rows; j++) {
            for (long k = 1L; k <= columns; k++) {
                availableSeats.add(new Seat(j, k));
            }
        }
        return availableSeats;
    }

    @JsonGetter(value = "available_seats")
    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    @JsonGetter(value = "total_rows")
    public Long getRows() {
        return rows;
    }

    public void setRows(Long rows) {
        this.rows = rows;
    }

    @JsonGetter(value = "total_columns")
    public Long getColumns() {
        return columns;
    }

    public void setColumns(Long columns) {
        this.columns = columns;
    }
}
