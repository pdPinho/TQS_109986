package ua.pt.CarApp;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.CoreMatchers.equalTo;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(CarController.class)
class CarControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @Test 
    void whenPostCar_thenCreateCar() throws Exception{
        Car car = new Car("maker","model");

        when(service.save(Mockito.any())).thenReturn(car);

        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
                .body(car)
            .when()
                .post("/api/cars")
            .then()
                .statusCode(201)
                .body("maker",equalTo("maker"));
    }
}