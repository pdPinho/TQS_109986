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

import ua.pt.Controllers.ReservationController;
import ua.pt.Domain.Bus;
import ua.pt.Domain.City;
import ua.pt.Domain.Reservation;
import ua.pt.Domain.Trip;
import ua.pt.Domain.User;
import ua.pt.Service.ReservationService;
import ua.pt.Service.TripService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private TripService tripService;

    @BeforeEach
    public void setUp() throws Exception {}

    private final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Test
    void whenPostReservation_thenCreateReservation() throws Exception {
        Trip trip = new Trip(new City("Porto"), new City("Aveiro"), new Date(), 2.5, new Bus("XPTO", 20));
        User user = new User("Pedro", "123123123");

        Reservation reservation = new Reservation("awaiting-payment", "EUR", trip, user);

        when(reservationService.save(Mockito.any())).thenReturn(reservation);

        mvc.perform(
            post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status", is("awaiting-payment")))
            .andExpect(jsonPath("$.trip.price", is(2.5)));

        
        verify(reservationService, times(1)).save(Mockito.any());
    }

    @Test
    void whenPayReservation_thenUpdateReservation() throws Exception {
        Reservation reservation = new Reservation("awaiting-payment", "EUR", new Trip(
            new City("Porto"), 
            new City("Aveiro"),
            new Date(), 
            2.5, 
            new Bus("XPTO", 20)), new User());
        reservation.setId((long)0);

        reservation.setStatus("paid");

        when(reservationService.updateReservationStatus(reservation.getId())).thenReturn(reservation);

        mvc.perform(
            post("/api/reservations/0/pay")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is("paid")));

        verify(reservationService, times(1)).updateReservationStatus(reservation.getId());
    }

    @Test
    void givenManyReservations_whenGetReservations_thenReturnJsonArray() throws Exception {
        Reservation reservation1 = new Reservation("paid", "EUR",new Trip(new City("Porto"), 
        new City("Aveiro"),
        new Date(), 
        2.5, 
        new Bus("XPTO", 20)), new User());
        Reservation reservation2 = new Reservation("paid", "EUR",new Trip(new City("Porto"), 
        new City("Aveiro"),
        new Date(), 
        3.5, 
        new Bus("XPTO", 20)), new User());
        Reservation reservation3 = new Reservation("awaiting-payment", "EUR",new Trip(new City("Porto"), 
        new City("Aveiro"),
        new Date(), 
        4.5, 
        new Bus("XPTO", 20)), new User());
        
        List<Reservation> allReservations = Arrays.asList(reservation1, reservation2, reservation3);

        when(reservationService.getAllReservations()).thenReturn(allReservations);

        mvc.perform(
            get("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].trip.price", is(2.5)))
            .andExpect(jsonPath("$[0].status", is("paid")))
            .andExpect(jsonPath("$[1].trip.price", is(3.5)))
            .andExpect(jsonPath("$[1].status", is("paid")))
            .andExpect(jsonPath("$[2].trip.price", is(4.5)))
            .andExpect(jsonPath("$[2].status", is("awaiting-payment")));

        verify(reservationService, times(1)).getAllReservations();
    
    }

    @Test
    void whenGetReservationsByTrip_thenReturnJsonArray() throws Exception {
        Trip trip = new Trip();
        trip.setId((long)1);

        Reservation reservation1 = new Reservation("paid", "EUR",trip, new User());
        Reservation reservation2 = new Reservation("paid", "EUR",trip, new User());

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        when(tripService.getTripById((long)1)).thenReturn(trip);
        when(reservationService.getReservationsByTrip(trip)).thenReturn(reservations);

        mvc.perform(
            get("/api/reservations/trip/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)));

        verify(tripService, times(1)).getTripById(trip.getId());
        verify(reservationService, times(1)).getReservationsByTrip(trip);
    }

    @Test
    void givenReservation_whenGetById_thenReturnReservation() throws Exception{
        Reservation reservation = new Reservation();
        reservation.setId((long)0);

        when(reservationService.getReservationById((long)0)).thenReturn(reservation);

        mvc.perform(
            get("/api/reservations/0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(0)));

        verify(reservationService, times(1)).getReservationById((long)0);
    }

    @Test
    void whenGetByInvalidId_thenReturnError() throws Exception{
        mvc.perform(
            get("/api/reservations/999")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(reservationService, times(1)).getReservationById((long)999);
    }

    /*
    @Test
    void whenChangingCurrency_thenReturnReservationWithNewCurrency() throws Exception{
        Reservation reservation = new Reservation();
        reservation.setCurrency("EUR");
        reservation.setId(10L);
        
        when(reservationService.getReservationById(10L)).thenReturn(reservation);

        mvc.perform(
            get("/api/reservations/10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.currency", is("EUR")));

        verify(reservationService, times(1)).getReservationById(reservation.getId());
    }
    */
}
