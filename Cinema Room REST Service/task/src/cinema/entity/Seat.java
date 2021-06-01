package cinema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {

    private Long row;
    private Long column;
    @JsonIgnore
    private Long price;
    @JsonIgnore
    private Boolean isBooked;

    public Seat() {
    }

    public Seat(Long row, Long column, Boolean isBooked) {
        this.row = row;
        this.column = column;
        this.price = (row <= 4L ? 10L : 8L);
        this.isBooked = isBooked;
    }

    public Long getRow() {
        return row;
    }

    public void setRow(Long row) {
        this.row = row;
    }

    public Long getColumn() {
        return column;
    }

    public void setColumn(Long column) {
        this.column = column;
    }

    @JsonIgnore
    public Boolean getBooked() {
        return isBooked;
    }

    public void setBooked(Boolean booked) {
        isBooked = booked;
    }

    @JsonIgnore
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
