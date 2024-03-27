package ua.pt.IntegrationTests;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import ua.pt.Service.ReservationService;
import ua.pt.Service.TripService;


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

    @Test
    void whenPostReservation_thenCreateReservation() throws Exception {
        Reservation reservation = new Reservation(0, 
                                        false, 
                                                    new Trip(
                                                        new City("Porto"), 
                                                        new City("Aveiro"),
                                                        new Date(), 
                                                        2.5, 
                                                        new Bus("XPTO", 20)));

        when(reservationService.save(Mockito.any())).thenReturn(reservation);

        mvc.perform(
            post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.classe_type", is(reservation.isClasse_type())))
            .andExpect(jsonPath("$.seat", is(reservation.getSeat())));

        verify(reservationService, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyReservations_whenGetReservations_thenReturnJsonArray() throws Exception {
        Reservation reservation1 = new Reservation(1, true, new Trip());
        Reservation reservation2 = new Reservation(0, true, new Trip());
        Reservation reservation3 = new Reservation(10, false, new Trip());
        
        List<Reservation> allReservations = Arrays.asList(reservation1, reservation2, reservation3);

        when(reservationService.getAllReservations()).thenReturn(allReservations);

        mvc.perform(
            get("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].classe_type", is(reservation1.isClasse_type())))
            .andExpect(jsonPath("$[0].seat", is(reservation1.getSeat())))
            .andExpect(jsonPath("$[1].classe_type", is(reservation2.isClasse_type())))
            .andExpect(jsonPath("$[1].seat", is(reservation2.getSeat())))
            .andExpect(jsonPath("$[2].classe_type", is(reservation3.isClasse_type())))
            .andExpect(jsonPath("$[2].seat", is(reservation3.getSeat())));

        verify(reservationService, times(1)).getAllReservations();
    
    }

    @Test
    void whenGetReservationsByTrip_thenReturnJsonArray() throws Exception {
        Trip trip = new Trip();
        trip.setId((long)1);

        Reservation reservation1 = new Reservation(1, true, trip);
        Reservation reservation2 = new Reservation(2, false, trip);

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
    void givenReservation_whenGetById_thenReturnUser() throws Exception{
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
