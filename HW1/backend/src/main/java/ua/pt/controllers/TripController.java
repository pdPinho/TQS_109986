package ua.pt.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.pt.domain.Trip;
import ua.pt.service.TripService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService){
        this.tripService = tripService;
    }

    @PostMapping("/trips")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip){
        HttpStatus status = HttpStatus.CREATED;
        Trip saved = tripService.save(trip);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/trips")
    public List<Trip> getAllTrips(){
        return tripService.getAllTrips();
    }

    @GetMapping("/trips/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable long id){
        Trip trip = tripService.getTripById(id);
        HttpStatus status;
        if (trip != null)
            status = HttpStatus.OK;
        else
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(trip, status);
    }

    
    @GetMapping("/trips/chosen")
    public List<Trip> findTripsByDateOriginDestination(
                                    @RequestParam("date") String date,
                                    @RequestParam("origin") Long origin,
                                    @RequestParam("destination") Long destination,
                                    @RequestParam("all") boolean all){

            return tripService.getTripsByDateAndOriginAndDestination(date, origin, destination, all);
    }
}