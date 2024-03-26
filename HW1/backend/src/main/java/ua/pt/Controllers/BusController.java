package ua.pt.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.pt.Domain.Bus;
import ua.pt.Service.BusService;

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
        HttpStatus status = HttpStatus.OK;
        Bus bus = busService.getBusById(id);
        return new ResponseEntity<>(bus, status);
    }
}