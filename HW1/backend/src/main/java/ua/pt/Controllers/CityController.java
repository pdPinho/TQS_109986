package ua.pt.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.pt.Domain.City;
import ua.pt.Service.CityService;

@RestController
@RequestMapping("/api")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService){
        this.cityService = cityService;
    }

    @PostMapping("/cities")
    public ResponseEntity<City> createCity(@RequestBody City city){
        HttpStatus status = HttpStatus.CREATED;
        City saved = cityService.save(city);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/cities")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<City> getCityById(@PathVariable long id){
        City city = cityService.getCityById(id);
        HttpStatus status;
        if (city != null)
            status = HttpStatus.OK;
        else
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(city, status);
    }

    @GetMapping("/cities/name/{name}")
    public ResponseEntity<City> getCityByName(@PathVariable String name){
        City city = cityService.getCityByName(name);
        HttpStatus status;
        if (city != null)
            status = HttpStatus.OK;
        else
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(city, status);
    }
}