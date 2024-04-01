package ua.pt.UnitTests;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.pt.domain.Bus;
import ua.pt.domain.City;
import ua.pt.domain.Trip;
import ua.pt.repository.CityRepository;
import ua.pt.repository.TripRepository;
import ua.pt.service.impl.TripServiceImpl;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {
    @Mock(lenient = true)
    private TripRepository tripRepository;

    @Mock(lenient = true)
    private CityRepository cityRepository;

    @InjectMocks
    private TripServiceImpl tripServiceImpl;

    City lisboa;
    City porto;
    Date date;
    Date dateForEmpty;

    @BeforeEach
    void setUp() throws ParseException{
        lisboa = new City("Lisboa");
        lisboa.setId(0L);
        porto = new City("Porto");
        porto.setId(1L);
        Bus bus1 = new Bus("XPTO", 10);
        Bus bus2 = new Bus("OTPX", 20);
        Bus bus3 = new Bus("SSSS", 3);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        date = formatter.parse("2024-04-01");

        dateForEmpty = formatter.parse("2024-04-05");

        Trip trip1 = new Trip(lisboa, porto, date, 2.5, bus1);
        trip1.setId(111L);
        Trip trip2 = new Trip(lisboa, porto, date, 3.5, bus2);
        Trip trip3 = new Trip(lisboa, new City("Faro"), date, 3.5, bus3);
        Trip trip4 = new Trip(lisboa, porto, date, 4.5, bus3);
        trip4.setOccupancy(3);

        List<Trip> allTrips = Arrays.asList(trip1, trip2, trip3);

        List<Trip> tripsForLisboaPortoOnDate = Arrays.asList(trip1, trip2);

        List<Trip> tripsForLisboaPortoOnDateAll = Arrays.asList(trip1, trip2, trip4);

        Mockito.when(cityRepository.findById(lisboa.getId())).thenReturn(Optional.of(lisboa));
        Mockito.when(cityRepository.findById(porto.getId())).thenReturn(Optional.of(porto));
        Mockito.when(tripRepository.findById(trip1.getId())).thenReturn(Optional.of(trip1));
        Mockito.when(tripRepository.findById(trip2.getId())).thenReturn(Optional.of(trip2));
        Mockito.when(tripRepository.findAll()).thenReturn(allTrips);
        Mockito.when(tripRepository.findTripsByDateAndOriginAndDestination(date, lisboa, porto)).thenReturn(tripsForLisboaPortoOnDate);
        Mockito.when(tripRepository.findTripsByDateAndOriginAndDestination(date, lisboa, porto)).thenReturn(tripsForLisboaPortoOnDateAll);
    }

    @Test
    void whenFindTripById_thenFoundIfExists(){
        long id = 111;
        Trip trip = tripServiceImpl.getTripById(id);

        assertThat(trip.getId()).isEqualTo(id);

        verify(tripRepository, times(1)).findById(trip.getId());
    }

    @Test
    void givenManyTrips_whenGetAll_thenReturnAll() {
        List<Trip> allTrips = tripServiceImpl.getAllTrips();

        assertThat(allTrips).hasSize(3);

        verify(tripRepository, times(1)).findAll();
    }

    @Test
    void givenManyTripsOnDateAndOriginAndDestination_whenGetDontAll_thenOnlyTripsOnDateAndOriginAndDestination() {
        List<Trip> allTrips = tripServiceImpl.getTripsByDateAndOriginAndDestination("2024-04-01", lisboa.getId(), porto.getId(), false);

        assertThat(allTrips).hasSize(2);

        verify(tripRepository, times(1)).findTripsByDateAndOriginAndDestination(date, lisboa, porto);
    }

    @Test
    void givenManyTripsOnDateAndOriginAndDestination_whenGetAll_thenOnlyTripsOnDateAndOriginAndDestination() {
        List<Trip> allTrips = tripServiceImpl.getTripsByDateAndOriginAndDestination("2024-04-01", lisboa.getId(), porto.getId(), true);

        assertThat(allTrips).hasSize(3);

        verify(tripRepository, times(1)).findTripsByDateAndOriginAndDestination(date, lisboa, porto);
    }

}
