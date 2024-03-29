package ua.pt.IntegrationTests;



import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ua.pt.Controllers.TripController;
import ua.pt.Domain.Bus;
import ua.pt.Domain.City;
import ua.pt.Domain.Trip;
import ua.pt.Service.TripService;


@WebMvcTest(TripController.class)
public class TripControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TripService tripService;

    @BeforeEach
    public void setUp() throws Exception {}

    @Test
    void whenPostTrip_thenCreateTrip() throws Exception {
        Trip trip = new Trip(new City("Lisboa"),
                            new City("Porto"),
                            new Date(),
                            2.5, 
                            new Bus("XPTO", 10));

        when(tripService.save(Mockito.any())).thenReturn(trip);

        mvc.perform(
            post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(trip)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.price", is(2.5)));

        verify(tripService, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyTrips_whenGetTrips_thenReturnJsonArray() throws Exception {
        Trip trip1 = new Trip(new City("Lisboa"),
        new City("Porto"),
        new Date(),
        2.5, 
        new Bus("XPTO", 10));
        Trip trip2 = new Trip(new City("Lisboa"),
        new City("Porto"),
        new Date(),
        3.5, 
        new Bus("XPTO", 10));

        List<Trip> allTrips = Arrays.asList(trip1, trip2);

        when(tripService.getAllTrips()).thenReturn(allTrips);

        mvc.perform(
            get("/api/trips")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].price", is(2.5)))
            .andExpect(jsonPath("$[1].price", is(3.5)));

        verify(tripService, times(1)).getAllTrips();
    }

    @Test
    void givenTrip_whenGetById_thenReturnTrip() throws Exception {
        Trip trip = new Trip(new City("Lisboa"),
        new City("Porto"),
        new Date(),
        2.5, 
        new Bus("XPTO", 10));
        trip.setId((long)0);


        when(tripService.getTripById((long)0)).thenReturn(trip);

        mvc.perform(
            get("/api/trips/0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(0)));

        verify(tripService, times(1)).getTripById(trip.getId());
    }


    @Test
    void whenGetByInvalidId_thenReturnError() throws Exception{
        mvc.perform(
            get("/api/trips/999")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(tripService, times(1)).getTripById((long)999);
    }

    @Test
    void givenManyTrips_whenGetByDateAndOriginAndDestination_thenReturnTripsBasedOnDateAndOriginAndDestination() throws Exception{
        City city1 = new City("Lisboa");
        city1.setId((long)0);
        City city2 = new City("Porto");
        city2.setId((long)1);
        City city3 = new City("Coimbra");
        city3.setId((long)2);
        
        Trip trip1 = new Trip(city1, city2, new Date(2024-04-01),2.5, new Bus("XPTO", 10));
        Trip trip2 = new Trip(city1, city2, new Date(2024-04-01),3.5, new Bus("XPTO", 10));
        Trip trip3 = new Trip(city1, city3, new Date(2024-04-01),5.5, new Bus("XPTO", 10));
        
        List<Trip> allTripsOnDayOne = Arrays.asList(trip1, trip2);

        when(tripService.getTripsByDateAndOriginAndDestination("2024-04-01", city1.getId(), city2.getId())).thenReturn(allTripsOnDayOne);

        mvc.perform(
            get("/api/trips/chosen?date=2024-04-01&origin=0&destination=1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].price", is(2.5)))
            .andExpect(jsonPath("$[1].price", is(3.5)));

        verify(tripService, times(1)).getTripsByDateAndOriginAndDestination("2024-04-01", (long)0, (long)1);


    }
}
