package ua.pt.UnitTests;


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

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ua.pt.controllers.ReservationController;
import ua.pt.domain.Bus;
import ua.pt.domain.City;
import ua.pt.domain.Reservation;
import ua.pt.domain.Trip;
import ua.pt.domain.User;
import ua.pt.service.ReservationService;
import ua.pt.service.TripService;
import ua.pt.service.UserService;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private TripService tripService;

    @MockBean
    private UserService userService;

    @Test
    void whenPostReservation_thenCreateReservation() throws Exception {
        Trip trip = new Trip(new City("Porto"), new City("Aveiro"), new Date(), 2.5, new Bus("XPTO", 20));
        User user = new User("Pedro", "123123123");

        Reservation reservation = new Reservation("awaiting-payment", "EUR", trip, user, 5.2);

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
            new Bus("XPTO", 20)), new User(), 5.2);
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
        new Bus("XPTO", 20)), new User(), 5.2);
        Reservation reservation2 = new Reservation("paid", "EUR",new Trip(new City("Porto"), 
        new City("Aveiro"),
        new Date(), 
        3.5, 
        new Bus("XPTO", 20)), new User(), 5.2);
        Reservation reservation3 = new Reservation("awaiting-payment", "EUR",new Trip(new City("Porto"), 
        new City("Aveiro"),
        new Date(), 
        4.5, 
        new Bus("XPTO", 20)), new User(), 5.2);
        
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

        Reservation reservation1 = new Reservation("paid", "EUR",trip, new User(), 5.2);
        Reservation reservation2 = new Reservation("paid", "EUR",trip, new User(), 5.2);

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
    void whenGetReservationsByUser_thenReturnJsonArray() throws Exception {
        User user = new User();
        user.setId((long)1);

        Reservation reservation1 = new Reservation("paid", "EUR",new Trip(), user, 5.2);
        Reservation reservation2 = new Reservation("paid", "EUR",new Trip(), user, 5.2);

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        when(userService.getUserById((long)1)).thenReturn(user);
        when(reservationService.getReservationsByUser(user)).thenReturn(reservations);

        mvc.perform(
            get("/api/reservations/user/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)));

        verify(userService, times(1)).getUserById(user.getId());
        verify(reservationService, times(1)).getReservationsByUser(user);
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
}
