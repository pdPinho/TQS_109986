package ua.pt.UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import ua.pt.Controllers.BusController;
import ua.pt.Domain.Bus;
import ua.pt.Service.BusService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@WebMvcTest(BusController.class)
public class BusControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BusService service;

    @BeforeEach
    public void setUp() throws Exception {}

    @Test
    void whenPostBus_thenCreateBus() throws Exception {
        Bus bus = new Bus("XPTO", 40);

        when(service.save(Mockito.any())).thenReturn(bus);

        mvc.perform(
            post("/api/buses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bus)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is("XPTO")))
            .andExpect(jsonPath("$.capacity", is(40)));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyBuses_whenGetBuses_thenReturnJsonArray() throws Exception {
        Bus bus1 = new Bus("XPTO", 20);
        Bus bus2 = new Bus("XPTO2", 30);
        Bus bus3 = new Bus("XPTOXPTO", 40);

        List<Bus> allBuses = Arrays.asList(bus1, bus2, bus3);

        when(service.getAllBuses()).thenReturn(allBuses);

        mvc.perform(
            get("/api/buses")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].name", is(bus1.getName())))
            .andExpect(jsonPath("$[0].capacity", is(bus1.getCapacity())))
            .andExpect(jsonPath("$[1].name", is(bus2.getName())))
            .andExpect(jsonPath("$[1].capacity", is(bus2.getCapacity())))
            .andExpect(jsonPath("$[2].name", is(bus3.getName())))
            .andExpect(jsonPath("$[2].capacity", is(bus3.getCapacity())));

        verify(service, times(1)).getAllBuses();
    }

    @Test
    void givenBus_whenGetById_thenReturnBus() throws Exception {
        Bus bus = new Bus("XPTO", 20);
        bus.setId((long)0);

        when(service.getBusById((long)0)).thenReturn(bus);

        mvc.perform(
            get("/api/buses/0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(0)));

        verify(service, times(1)).getBusById(bus.getId());
    }

    @Test
    void whenGetByInvalidId_thenReturnError() throws Exception {
        mvc.perform(
            get("/api/buses/999")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(service, times(1)).getBusById((long)999);
    }
}
