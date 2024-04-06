package ua.pt.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ua.pt.domain.City;
import ua.pt.service.CityService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService){
        this.cityService = cityService;
    }

    @Operation(summary = "Create a new city")
    @ApiResponse(responseCode="201", description="City created")
    @PostMapping("/cities")
    public ResponseEntity<City> createCity(@RequestBody City city){
        HttpStatus status = HttpStatus.CREATED;
        City saved = cityService.save(city);
        return new ResponseEntity<>(saved, status);
    }

    @Operation(summary = "Get all cities")
    @ApiResponse(responseCode="200", description="List of cities retrieved")
    @GetMapping("/cities")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @Operation(summary = "Get city by ID")
    @ApiResponse(responseCode="200", description="Retrieved city by ID")
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

    @Operation(summary = "Get city by name")
    @ApiResponse(responseCode="200", description="Retrieved city by name")
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