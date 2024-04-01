package ua.pt.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.pt.domain.Reservation;
import ua.pt.domain.Trip;
import ua.pt.domain.User;
import ua.pt.service.ReservationService;
import ua.pt.service.TripService;
import ua.pt.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ReservationController {
    private final ReservationService reservationService;
    private final TripService tripService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService, 
                                TripService tripService,
                                UserService userService){
        this.reservationService = reservationService;
        this.tripService = tripService;
        this.userService = userService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        HttpStatus status = HttpStatus.CREATED;
        Reservation saved = reservationService.save(reservation);
        return new ResponseEntity<>(saved, status);
    }

    @PostMapping("/reservations/{id}/pay")
    public ResponseEntity<Reservation> updateReservationStatus(@PathVariable Long id){
        HttpStatus status = HttpStatus.OK;
        Reservation saved = reservationService.updateReservationStatus(id);
        return new ResponseEntity<>(saved, status);
    }
    
    @GetMapping("/reservations")
    public List<Reservation> getAllReservations(){
        return reservationService.getAllReservations();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable long id){
        Reservation reservation = reservationService.getReservationById(id);
        HttpStatus status;
        if (reservation != null)
            status = HttpStatus.OK;
        else
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(reservation, status);
    }

    @GetMapping("/reservations/trip/{id}")
    public List<Reservation> getAllReservationsByTrip(@PathVariable long id){
        Trip trip = tripService.getTripById(id);
        return reservationService.getReservationsByTrip(trip);
    }

    @GetMapping("/reservations/user/{id}")
    public List<Reservation> getAllReservationsByUser(@PathVariable long id){
        User user = userService.getUserById(id);
        return reservationService.getReservationsByUser(user);
    }

}