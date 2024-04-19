package ua.pt.lab7_4;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CarController {
    CarManagerService carManagerService;

    public CarController(CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createEmployee(@RequestBody Car car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carManagerService.save(car);
        return new ResponseEntity<>(saved, status);
    }


    @GetMapping(path="/cars")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping(path="/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id){
        HttpStatus status = HttpStatus.OK;
        Optional<Car> car = carManagerService.getCarDetails(id);
        if (car.isPresent())
            return new ResponseEntity<>(car.get(), status);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
