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
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ua.pt.Controllers.CityController;
import ua.pt.Domain.City;
import ua.pt.Service.CityService;

@WebMvcTest(CityController.class)
public class CityControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService service;

    @BeforeEach
    public void setUp() throws Exception {}

    @Test
    void whenPostCity_thenCreateCity() throws Exception {
        City city = new City("Porto");

        when(service.save(Mockito.any())).thenReturn(city);

        mvc.perform(
            post("/api/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(city)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is("Porto")));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCities_whenGetCities_thenReturnJsonArray() throws Exception {
        City city1 = new City("Porto");
        City city2 = new City("Lisboa");
        City city3 = new City("Coimbra");

        List<City> allCities = Arrays.asList(city1, city2, city3);

        when(service.getAllCities()).thenReturn(allCities);

        mvc.perform(
            get("/api/cities")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].name", is(city1.getName())))
            .andExpect(jsonPath("$[1].name", is(city2.getName())))
            .andExpect(jsonPath("$[2].name", is(city3.getName())));

        verify(service, times(1)).getAllCities();
    }

    @Test
    void givenCity_whenGetById_thenReturnCity() throws Exception {
        City city = new City("Porto");
        city.setId((long)0);

        when(service.getCityById((long)0)).thenReturn(city);

        mvc.perform(
            get("/api/cities/0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(0)));

        verify(service, times(1)).getCityById(city.getId());
    }

    @Test
    void givenCity_whenGetByName_thenReturnCity() throws Exception {
        City city = new City("Porto");

        when(service.getCityByName("Porto")).thenReturn(city);

        mvc.perform(
            get("/api/cities/name/Porto")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Porto")));

        verify(service, times(1)).getCityByName("Porto");
    }

    @Test
    void whenGetByInvalidId_thenReturnError() throws Exception {
        mvc.perform(
            get("/api/cities/999")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(service, times(1)).getCityById((long)999);
    }

    @Test
    void whenGetByInvalidName_thenReturnError() throws Exception {
        mvc.perform(
            get("/api/cities/name/Porto")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(service, times(1)).getCityByName("Porto");
    }
}
