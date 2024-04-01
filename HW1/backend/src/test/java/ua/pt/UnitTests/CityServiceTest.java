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

import ua.pt.domain.City;
import ua.pt.repository.CityRepository;
import ua.pt.service.impl.CityServiceImpl;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {
    @Mock(lenient = true)
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityServiceImpl;

    @BeforeEach
    void setUp(){
        City city1 = new City("Lisboa");
        city1.setId((long)111);

        City city2 = new City("Porto");

        List<City> allCities = Arrays.asList(city1, city2);

        Mockito.when(cityRepository.findById(city1.getId())).thenReturn(Optional.of(city1));
        Mockito.when(cityRepository.findByName(city1.getName())).thenReturn(city1);
        Mockito.when(cityRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(cityRepository.findByName("wrong")).thenReturn(null);
        Mockito.when(cityRepository.findAll()).thenReturn(allCities);
    }

    @Test
    void whenFindCityById_thenFoundIfExists(){
        long id = 111;
        City city = cityServiceImpl.getCityById(id);

        assertThat(city.getId()).isEqualTo(id);

        verify(cityRepository, times(1)).findById(city.getId());
    }

    @Test
    void whenFindCityByName_thenFoundIfExists(){
        String name = "Lisboa";
        City city = cityServiceImpl.getCityByName(name);

        assertThat(city.getName()).isEqualTo(name);

        verify(cityRepository, times(1)).findByName(name);
    }

    @Test
    void whenInvalidId_thenShouldNotBeFound(){
        City city = cityServiceImpl.getCityById(-99L);

        assertThat(city).isNull();

        verify(cityRepository, times(1)).findById(-99L);
    }

    @Test
    void whenInvalidName_thenShouldNotBeFound() {
        City city = cityServiceImpl.getCityByName("wrong");

        assertThat(city).isNull();

        verify(cityRepository, times(1)).findByName("wrong");
    }

    @Test
    void givenManyCities_whenGetAll_thenReturnAll() {
        List<City> allCities = cityServiceImpl.getAllCities();

        assertThat(allCities).hasSize(2);

        verify(cityRepository, times(1)).findAll();
    }
}
