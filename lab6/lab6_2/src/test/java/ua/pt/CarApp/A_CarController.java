package ua.pt.CarApp;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@WebMvcTest(CarController.class)
public class A_CarController {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService service;

    @Test
    void createCarByPost() throws Exception{
        Car car = new Car("maker", "ferrari");

        when(service.save(Mockito.any())).thenReturn(car);

        mockMvc.perform(post("/api/cars")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJson(car)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.model", is("ferrari")));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void returnMultipleCarsOnGet() throws Exception{
        Car car1 = new Car("maker1", "model1");
        Car car2 = new Car("maker2", "model2");
        Car car3 = new Car("maker3", "model3");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        when(service.getAllCars()).thenReturn(allCars);

        mockMvc.perform(get("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(3)))
                        .andExpect(jsonPath("$[0].model", is(car1.getModel())))
                        .andExpect(jsonPath("$[1].model", is(car2.getModel())))
                        .andExpect(jsonPath("$[2].model", is(car3.getModel())));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void findCarByIdReturnsCarOnGet() throws Exception{
        Car car = new Car("maker", "ferrari");
        car.setCarId((long)0);

        when(service.getCarDetails(0)).thenReturn(Optional.of(car));
        

        mockMvc.perform(get("/api/cars/0")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.model", is("ferrari")));

        verify(service, times(1)).getCarDetails(0);
    }

    @Test
    void onGetFindCarNotExistsThenReturnsNotOk() throws Exception {
        when(service.getCarDetails(0)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cars/0")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());

        verify(service, times(1)).getCarDetails(0);
    }
}
