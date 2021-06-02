package cinema.controller;


import cinema.aop.Authenticate;
import cinema.entity.CinemaRoom;
import cinema.entity.ReturnRequest;
import cinema.entity.Seat;
import cinema.entity.Stats;
import cinema.exception_handling.BadRequestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/")
public class CinemaController {
    private final Long ROW = 9L;
    private final Long COLUMNS = 9L;

    private final String ALREADY_PURCHASED = "The ticket has been already purchased!";
    private final String WRONG_BOUNDS = "The number of a row or a column is out of bounds!";
    private final String NO_SUCH_TOKEN = "Wrong token!";

    CinemaRoom cinemaRoom = new CinemaRoom(ROW,COLUMNS);
    Stats stats = Stats.getInstance(ROW * COLUMNS);


    @Authenticate(isAuth = false)
    @GetMapping("/seats")
    public CinemaRoom showCinemaRoom() {
        return cinemaRoom;
    }

    @Authenticate(isAuth = false)
    @PostMapping("/purchase")
    public ResponseEntity<Object> bookSeat(@RequestBody Seat seat) throws Throwable {
        if (seat.getColumn() < 1 || seat.getColumn() > cinemaRoom.getColumns()
        || seat.getRow() < 1 || seat.getRow() > cinemaRoom.getRows()) {
            throw new BadRequestError(WRONG_BOUNDS);
        }
        Seat seatCheck = null;
        for (Seat s : cinemaRoom.getAllSeats()) {
            if (s.getRow().equals(seat.getRow())
                    && s.getColumn().equals(seat.getColumn())) {
                seatCheck = s;
            }
        }
        if (seatCheck.getBooked()) {
            throw new BadRequestError(ALREADY_PURCHASED);
        }
        seatCheck.setBooked(true);
        seatCheck.setToken(UUID.randomUUID().toString());
        Map<String, Object> resp = new HashMap<>();
        resp.put("token", seatCheck.getToken());
        resp.put("ticket", seatCheck);
        stats.purchaseTicket(seatCheck);
       return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Authenticate(isAuth = false)
    @PostMapping("/return")
    public ResponseEntity<Object> unBookSeat(@RequestBody ReturnRequest returnRequest)
            throws Throwable {
        Seat seatCheck = null;
        for (Seat s : cinemaRoom.getAllSeats()) {
            if (s.getToken().equals(returnRequest.getToken())) {
                seatCheck = s;
            }
        }
        if (seatCheck == null) {
            throw new BadRequestError(NO_SUCH_TOKEN);
        } else {
            seatCheck.setToken("");
            seatCheck.setBooked(false);
            Map<String, Object> resp = new HashMap<>();
            resp.put("ticket", seatCheck);
            stats.returnTicket(seatCheck);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }

    @Authenticate(isAuth = true)
    @PostMapping("/stats")
    public ResponseEntity<Object> showStatistics(@RequestParam(required = false) String password) {
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
