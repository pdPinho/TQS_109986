package ua.pt.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/buses")
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus){
        HttpStatus status = HttpStatus.CREATED;
        Bus saved = busService.save(bus);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/buses")
    public List<Bus> getAllBuses() {
        return busService.getAllBuses();
    }

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