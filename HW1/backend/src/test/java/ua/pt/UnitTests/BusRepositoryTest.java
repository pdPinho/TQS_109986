package ua.pt.UnitTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ua.pt.Domain.Bus;
import ua.pt.Repository.BusRepository;

@DataJpaTest
public class BusRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BusRepository busRepository;

    @Test
    void whenFindBusById_thenReturnBus() {
        Bus bus = new Bus("XPTO", 10);
        bus.setId(10L);

        entityManager.persistAndFlush(bus);

        Bus found = busRepository.findById(10L).get();
        assertThat(found).isEqualTo(bus);
    }

    @Test
    void whenFindBusByInvalidId_thenReturnNoExists() {
        Bus found = busRepository.findById(1L).get();
        assertThat(found).isNull();
    }

    @Test
    void whenFindBusByName_thenReturnBus(){
        Bus bus = new Bus("XPTO", 10);

        entityManager.persistAndFlush(bus);

        Bus found = busRepository.findByName("XPTO");
        assertThat(found).isEqualTo(bus);
    }

    @Test
    void whenFindBusByInvalidName_thenReturnNoExists() {
        Bus found = busRepository.findByName("XPTO");
        assertThat(found).isNull();
    }

    @Test
    void givenSetOfBuses_whenFindAll_thenReturnAllBuses() {
        Bus bus = new Bus("XPTO", 10);
        Bus bus2 = new Bus("XPTO22", 20);

        entityManager.persist(bus);
        entityManager.persistAndFlush(bus2);

        List<Bus> allBuses = busRepository.findAll();

        assertThat(allBuses).hasSize(2)
                            .extracting(Bus::getName).containsOnly(bus.getName(), bus2.getName());
    }
}
