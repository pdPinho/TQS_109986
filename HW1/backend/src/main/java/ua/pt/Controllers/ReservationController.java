package ua.pt.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.pt.Domain.Reservation;
import ua.pt.Domain.Trip;
import ua.pt.Service.ReservationService;
import ua.pt.Service.TripService;

@RestController
@RequestMapping("/api")
public class ReservationController {
    private final ReservationService reservationService;
    private final TripService tripService;

    public ReservationController(ReservationService reservationService, TripService tripService){
        this.reservationService = reservationService;
        this.tripService = tripService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        HttpStatus status = HttpStatus.CREATED;
        Reservation saved = reservationService.save(reservation);
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
}