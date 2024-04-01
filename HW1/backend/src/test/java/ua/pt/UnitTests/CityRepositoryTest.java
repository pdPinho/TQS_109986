package ua.pt.UnitTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ua.pt.domain.City;
import ua.pt.repository.CityRepository;

@DataJpaTest
class CityRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    @Test
    void whenFindCityById_thenReturnCity() {
        City city = new City("Lisboa");

        entityManager.persistAndFlush(city);

        City found = cityRepository.findById(city.getId()).get();
        assertThat(found).isEqualTo(city);
    }

    @Test
    void whenFindCityByInvalidId_thenReturnNoExists() {
        Optional<City> found = cityRepository.findById(1L);
        assertThat(found).isEmpty();
    }

    @Test
    void whenFindCityByName_thenReturnCity(){
        City city = new City("Porto");

        entityManager.persistAndFlush(city);

        City found = cityRepository.findByName("Porto");
        assertThat(found).isEqualTo(city);
    }

    @Test
    void whenFindCityByInvalidName_thenReturnNoExists() {
        City found = cityRepository.findByName("XPTO");
        assertThat(found).isNull();
    }

    @Test
    void givenSetOfCities_whenFindAll_thenReturnAllCities() {
        City city = new City("Porto");
        City city2 = new City("Lisboa");

        entityManager.persist(city);
        entityManager.persistAndFlush(city2);

        List<City> allCities = cityRepository.findAll();

        assertThat(allCities).hasSize(2)
                            .extracting(City::getName).containsOnly(city.getName(), city2.getName());
    }
}
