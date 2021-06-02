package cinema.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Stats {
    private Long income;
    private Long seats;
    private Long tickets;
    private static Stats instance;

    private Stats(Long size) {
        this.seats = size;
        this.tickets = 0L;
        this.income = 0L;
    }

    public static Stats getInstance(Long size) {
        if (instance == null) {
            instance = new Stats(size);
        }
        return instance;
    }

    @JsonGetter(value = "current_income")
    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    @JsonGetter(value = "number_of_available_seats")
    public Long getSeats() {
        return seats;
    }

    public void setSeats(Long seats) {
        this.seats = seats;
    }

    @JsonGetter(value = "number_of_purchased_tickets")
    public Long getTickets() {
        return tickets;
    }

    public void setTickets(Long tickets) {
        this.tickets = tickets;
    }

    @JsonIgnore
    public void purchaseTicket(Seat seat) {
        this.tickets++;
        this.seats--;
        this.income += seat.getPrice();
    }

    public void returnTicket(Seat seat) {
        this.tickets--;
        this.seats++;
        this.income -= seat.getPrice();
    }
}
