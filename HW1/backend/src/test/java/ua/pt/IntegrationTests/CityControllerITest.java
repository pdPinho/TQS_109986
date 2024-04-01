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
import ua.pt.domain.City;
import ua.pt.repository.CityRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = BusApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CityControllerITest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CityRepository repository;

    private final Logger logger = LoggerFactory.getLogger(CityControllerITest.class);

    @AfterEach
    public void resetInit(){
        repository.deleteAll();
    }

    @Test
    void givenManyCities_whenGetCities_returnCities() throws IOException, Exception{
        logger.info("===========================================");
        logger.info("Starting test givenManyCities_whenGetCities_returnCities...");

        City city1 = new City("Lisboa");
        City city2 = new City("Porto");

        logger.info("POST requests sent to create cities");
        mvc.perform(
            post("/api/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(city1)));

        logger.info("City 1 created");

        mvc.perform(
            post("/api/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(city2)));

        logger.info("City 2 created");

        logger.info("Retrieving cities from the repository");
        
        List<City> found = repository.findAll();
        
        if (!found.isEmpty())
            logger.info("We found " + found.size() + " cities in the repository");
        else
            logger.warn("No cities found in the repository");

        assertThat(found).hasSize(2);

        logger.info("Test completed successfully.");
        logger.info("===========================================");
    }
}
