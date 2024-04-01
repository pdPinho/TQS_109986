package ua.pt.UnitTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.pt.domain.Bus;
import ua.pt.repository.BusRepository;
import ua.pt.service.impl.BusServiceImpl;

@ExtendWith(MockitoExtension.class)
class BusServiceTest {
    @Mock(lenient = true)
    private BusRepository busRepository;

    @InjectMocks
    private BusServiceImpl busServiceImpl;

    @BeforeEach
    void setUp(){
        Bus bus1 = new Bus("XPTO", 10);
        bus1.setId((long)111);

        Bus bus2 = new Bus("Flixbus", 20);

        List<Bus> allBuses = Arrays.asList(bus1, bus2);

        Mockito.when(busRepository.findById(bus1.getId())).thenReturn(Optional.of(bus1));
        Mockito.when(busRepository.findByName(bus1.getName())).thenReturn(bus1);
        Mockito.when(busRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(busRepository.findByName("wrong")).thenReturn(null);
        Mockito.when(busRepository.findAll()).thenReturn(allBuses);
    }

    @Test
    void whenFindBusById_thenFoundIfExists(){
        long id = 111;
        Bus bus = busServiceImpl.getBusById(id);

        assertThat(bus.getId()).isEqualTo(id);

        verify(busRepository, times(1)).findById(bus.getId());
    }

    @Test
    void whenFindBusByName_thenFoundIfExists(){
        String name = "XPTO";
        Bus bus = busServiceImpl.getBusByName(name);

        assertThat(bus.getName()).isEqualTo(name);

        verify(busRepository, times(1)).findByName(name);
    }

    @Test
    void whenInvalidId_thenShouldNotBeFound(){
        Bus bus = busServiceImpl.getBusById(-99L);

        assertThat(bus).isNull();

        verify(busRepository, times(1)).findById(-99L);
    }

    @Test
    void whenInvalidName_thenShouldNotBeFound() {
        Bus bus = busServiceImpl.getBusByName("wrong");

        assertThat(bus).isNull();

        verify(busRepository, times(1)).findByName("wrong");
    }

    @Test
    void givenManyBuses_whenGetAll_thenReturnAll() {
        List<Bus> allBuses = busServiceImpl.getAllBuses();

        assertThat(allBuses).hasSize(2);

        verify(busRepository, times(1)).findAll();
    }
}
