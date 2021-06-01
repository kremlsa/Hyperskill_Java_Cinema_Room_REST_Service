package cinema.controller;


import cinema.entity.CinemaRoom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class CinemaController {

    @GetMapping("/seats")
    public CinemaRoom showCinemaRoom() {
        CinemaRoom cinemaRoom = new CinemaRoom(9L,9L);
        return cinemaRoom;
    }
}
