package ua.pt.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ua.pt.service.CurrencyService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService){
        this.currencyService = currencyService;
    }

    @Operation(summary = "Convert price based on currency")
    @ApiResponse(responseCode="200", description="Price retrieved")
    @ApiResponse(responseCode="500", description="Currency does not exist")
    @GetMapping("/currency/{name}")
    public double convertCurrency(@PathVariable String name, @RequestParam("price") double price){
        return currencyService.convertPrice(price, name);
    }
}
