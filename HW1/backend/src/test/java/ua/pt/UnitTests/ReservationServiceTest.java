package ua.pt.UnitTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.pt.Domain.Bus;
import ua.pt.Domain.City;
import ua.pt.Domain.Reservation;
import ua.pt.Domain.Trip;
import ua.pt.Domain.User;
import ua.pt.Repository.ReservationRepository;
import ua.pt.Repository.TripRepository;
import ua.pt.Service.Impl.ReservationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock(lenient = true)
    private ReservationRepository reservationRepository;

    @Mock(lenient = true)
    private TripRepository tripRepository;

    @InjectMocks
    private ReservationServiceImpl reservationServiceImpl;

    @BeforeEach
    void setUp(){
        User user = new User("Pedro", "123123123");
        user.setId(111L);
        City city1 = new City("Porto");
        City city2 = new City("Lisboa");
        Trip trip = new Trip(city1, city2, new Date(), 2.5, new Bus());
        trip.setId(111L);
        Reservation reservation1 = new Reservation("awaiting-payment", "EUR", trip, user);
        reservation1.setId((long)111);

        Reservation reservation2 = new Reservation();

        List<Reservation> allReservations = Arrays.asList(reservation1, reservation2);

        List<Reservation> allReservationsByTrip = Arrays.asList(reservation1);

        Mockito.when(reservationRepository.findById(reservation1.getId())).thenReturn(Optional.of(reservation1));
        Mockito.when(reservationRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(reservationRepository.findAll()).thenReturn(allReservations);
        Mockito.when(tripRepository.findById(111L)).thenReturn(Optional.of(trip));
        Mockito.when(reservationRepository.findAllByTrip(trip)).thenReturn(allReservationsByTrip);
    }

    @Test
    void whenFindReservationById_thenFoundIfExists(){
        long id = 111;
        Reservation reservation = reservationServiceImpl.getReservationById(id);

        assertThat(reservation.getId()).isEqualTo(id);

        verify(reservationRepository, times(1)).findById(reservation.getId());
    }

    @Test
    void whenInvalidId_thenShouldNotBeFound(){
        Reservation reservation = reservationServiceImpl.getReservationById(-99L);

        assertThat(reservation).isNull();

        verify(reservationRepository, times(1)).findById(-99L);
    }

    @Test
    void givenManyReservations_whenGetAll_thenReturnAll() {
        List<Reservation> allReservations = reservationServiceImpl.getAllReservations();

        assertThat(allReservations).hasSize(2);

        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void givenManyReservations_whenGetByTrip_thenReturnReservationsOnTrip(){
        Trip trip = tripRepository.findById(111L).get();

        List<Reservation> allReservationsByTrip = reservationServiceImpl.getReservationsByTrip(trip);

        assertThat(allReservationsByTrip).hasSize(1);

        verify(reservationRepository, times(1)).findAllByTrip(trip);
    }

    @Test
    void givenReservation_whenPostUpdateReservation_thenReturnUpdatedReservation() {
        reservationServiceImpl.updateReservationStatus(111L);

        verify(reservationRepository, times(1)).save(Mockito.any());
    }
}
