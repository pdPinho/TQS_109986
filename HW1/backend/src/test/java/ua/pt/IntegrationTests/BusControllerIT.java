package ua.pt.IntegrationTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import ua.pt.BusApplication;
import ua.pt.Domain.Bus;
import ua.pt.Repository.BusRepository;
import ua.pt.Repository.TripRepository;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = BusApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
 //@TestPropertySource(locations = "application-integrationtest.properties")
public class BusControllerIT {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BusRepository busRepository;

    @AfterEach
    public void resetDb(){
        busRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateBus() throws IOException, Exception{
        Bus bus = new Bus("XPTO", 123);
        mvc.perform(
            post("/buses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bus)));

        List<Bus> found = busRepository.findAll();
        assertThat(found).hasSize(1);
    }
}
