package cinema.entity;



import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CinemaRoom {

    private Long rows;
    private Long columns;
    private List<Seat> allSeats;

    public CinemaRoom(Long rows, Long columns) {
        this.rows = rows;
        this.columns = columns;
        this.allSeats = setAllSeats();
    }

    public CinemaRoom() {
    }

    public List<Seat> setAllSeats() {
        allSeats = new ArrayList<>();
        for (long j = 1L; j <= rows; j++) {
            for (long k = 1L; k <= columns; k++) {
                allSeats.add(new Seat(j, k, false));
            }
        }
        return allSeats;
    }

    @JsonIgnore
    public List<Seat> getAllSeats() {
        return allSeats;
    }

    @JsonGetter(value = "available_seats")
    public List<Seat> getAvailableSeats() {
        return allSeats
                .stream()
                .filter(x -> !x.getBooked())
                .collect(Collectors.toList());
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
