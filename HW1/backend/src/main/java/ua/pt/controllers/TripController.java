package ua.pt.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Create a new trip")
    @ApiResponse(responseCode="201", description="Trip created")
    @PostMapping("/trips")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip){
        HttpStatus status = HttpStatus.CREATED;
        Trip saved = tripService.save(trip);
        return new ResponseEntity<>(saved, status);
    }

    @Operation(summary = "Get all trips")
    @ApiResponse(responseCode="200", description="Retrieved list of trips")
    @GetMapping("/trips")
    public List<Trip> getAllTrips(){
        return tripService.getAllTrips();
    }

    @Operation(summary = "Get trip by ID")
    @ApiResponse(responseCode="200", description="Retrieved trip by ID")
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

    @Operation(summary = "Get trips based on origin, destination, date and if or not full")
    @ApiResponse(responseCode="200", description="Retrieved list of trips based on given parameters")
    @GetMapping("/trips/chosen")
    public List<Trip> findTripsByDateOriginDestination(
                                    @RequestParam("date") String date,
                                    @RequestParam("origin") Long origin,
                                    @RequestParam("destination") Long destination,
                                    @RequestParam("all") boolean all){

            return tripService.getTripsByDateAndOriginAndDestination(date, origin, destination, all);
    }
}