package cinema.controller;


import cinema.entity.CinemaRoom;
import cinema.entity.ReturnRequest;
import cinema.entity.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    private String NO_SUCH_TOKEN = "{\n" +
            "    \"error\": \"Wrong token!\"\n" +
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
        Seat seatCheck = null;
        for (Seat s : cinemaRoom.getAllSeats()) {
            if (s.getRow().equals(seat.getRow())
                    && s.getColumn().equals(seat.getColumn())) {
                seatCheck = s;
            }
        }
        if (seatCheck.getBooked()) {
            return new ResponseEntity<>(ALREADY_PURCHASED, HttpStatus.BAD_REQUEST);
        }
        seatCheck.setBooked(true);
        seatCheck.setToken(UUID.randomUUID().toString());
        Map<String, Object> resp = new HashMap<>();
        resp.put("token", seatCheck.getToken());
        resp.put("ticket", seatCheck);
       return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<Object> unBookSeat(@RequestBody ReturnRequest returnRequest) {
        Seat seatCheck = null;
        for (Seat s : cinemaRoom.getAllSeats()) {
            if (s.getToken().equals(returnRequest.getToken())) {
                seatCheck = s;
            }
        }
        if (seatCheck == null) {
            return new ResponseEntity<>(NO_SUCH_TOKEN, HttpStatus.BAD_REQUEST);
        } else {
            seatCheck.setToken("");
            seatCheck.setBooked(false);
            Map<String, Object> resp = new HashMap<>();
            resp.put("ticket", seatCheck);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }
}
