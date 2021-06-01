package cinema.controller;


import cinema.entity.CinemaRoom;
import cinema.entity.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class CinemaController {
    private String ALREADY_PURCHASED = "{\n" +
            "    \"error\": \"The ticket has been already purchased!\"\n" +
            "}";
    private String WRONG_BOUNDS = "{\n" +
            "    \"error\": \"The number of a row or a column is out of bounds!\"\n" +
            "}";

    CinemaRoom cinemaRoom = new CinemaRoom(9L,9L);

    @GetMapping("/seats")
    public CinemaRoom showCinemaRoom() {
        return cinemaRoom;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> bookSeat(@RequestBody Seat seat) {
        if (seat.getColumn() < 1 || seat.getColumn() > cinemaRoom.getColumns()
        || seat.getRow() < 1 || seat.getRow() > cinemaRoom.getRows()) {
            return new ResponseEntity<>(WRONG_BOUNDS, HttpStatus.BAD_REQUEST);
        }
        List<Seat> seatCheck = cinemaRoom
               .getAvailableSeats()
               .stream()
               .filter( x ->
                       x.getRow().equals(seat.getRow()) && x.getColumn().equals(seat.getColumn()))
               .collect(Collectors.toList());
        if (seatCheck.get(0).getBooked()) {
            return new ResponseEntity<>(ALREADY_PURCHASED, HttpStatus.BAD_REQUEST);
        }
        seatCheck.get(0).setBooked(true);
       return new ResponseEntity<>(seatCheck.get(0), HttpStatus.OK);
    }
}
