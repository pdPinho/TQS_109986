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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import ua.pt.BusApplication;
import ua.pt.domain.Bus;
import ua.pt.repository.BusRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = BusApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class BusControllerITest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BusRepository repository;

    private final Logger logger = LoggerFactory.getLogger(BusControllerITest.class);

    @AfterEach
    public void resetInit(){
        repository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateBus() throws IOException, Exception{
        logger.info("===========================================");
        logger.info("Starting test whenValidInput_thenCreateBus...");

        Bus bus = new Bus("Flixbus", 123);

        logger.info("POST request sent to create bus");
        mvc.perform(
            post("/api/buses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bus)));

        logger.info("Retrieving buses from the repository");
        
        List<Bus> found = repository.findAll();
        
        if (!found.isEmpty())
            logger.info("Found " + found.size() + " buses and Bus Name - " + found.get(0).getName());
        else
            logger.warn("No buses found in the repository");

        assertThat(found).hasSize(1);

        logger.info("Test completed successfully.");
        logger.info("===========================================");
    }
}
