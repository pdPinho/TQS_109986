package ua.pt.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.*;
import ua.pt.domain.Bus;
import ua.pt.service.BusService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class BusController {
    private final BusService busService;

    public BusController(BusService busService){
        this.busService = busService;
    }

    @Operation(summary = "Create a new bus")
    @ApiResponses(value = {
        @ApiResponse(responseCode="201", description="Bus created successfully",
        content={@Content(mediaType="application/json", schema=@Schema(implementation=Bus.class))}),
        @ApiResponse(responseCode="400", description="Failed to create bus due to invalid data", content=@Content),
        @ApiResponse(responseCode="500", description="Couldn't create bus", content=@Content)})

    @PostMapping("/buses")
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus){
        HttpStatus status = HttpStatus.CREATED;
        Bus saved = busService.save(bus);
        return new ResponseEntity<>(saved, status);
    }

    @Operation(summary = "Get all buses")
    @ApiResponse(responseCode="200", description="List of buses retrieved successfully")
    @GetMapping("/buses")
    public List<Bus> getAllBuses() {
        return busService.getAllBuses();
    }

    @Operation(summary = "Get bus by ID")
    @ApiResponse(responseCode = "200", description = "Bus found and retrieved successfully")
    @GetMapping("/buses/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable long id){
        Bus bus = busService.getBusById(id);
        HttpStatus status;
        if (bus != null)
            status = HttpStatus.OK;
        else
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(bus, status);
    }
}