package ua.pt.IntegrationTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.Date;
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

import jakarta.transaction.Transactional;
import ua.pt.BusApplication;
import ua.pt.domain.Bus;
import ua.pt.domain.City;
import ua.pt.domain.Trip;
import ua.pt.repository.BusRepository;
import ua.pt.repository.CityRepository;
import ua.pt.repository.TripRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = BusApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class TripControllerITest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private TripRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private BusRepository busRepository;

    private final Logger logger = LoggerFactory.getLogger(TripControllerITest.class);

    @AfterEach
    public void resetInit(){
        repository.deleteAll();
        cityRepository.deleteAll();
        busRepository.deleteAll();
    }

    @Transactional
    @Test
    void givenManyTrips_whenGetTripsByDateAndOriginAndDestination_returnTripsBasedOnThat() throws IOException, Exception{
        logger.info("===========================================");
        logger.info("Starting test givenManyTrips_whenGetTripsByDateAndOriginAndDestination_returnTripsBasedOnThat...");

        City porto = new City("Porto");
        City lisboa = new City("Lisboa");
        Date date = new Date(2024-04-01);
        Bus bus = new Bus("XPTO", 10);
        
        busRepository.save(bus);
        cityRepository.save(porto);
        cityRepository.save(lisboa);
            
        Trip trip1 = new Trip(porto, lisboa, date, 2.5, bus);
        Trip trip2 = new Trip(porto, lisboa, date, 2.5, bus);
        Trip trip3 = new Trip(lisboa, porto, date, 2.5, bus);
        
        logger.info("POST requests sent to create trips");
        mvc.perform(
            post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(trip1)));

        logger.info("Trip 1 created");

        mvc.perform(
            post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(trip2)));

        logger.info("Trip 2 created");

        mvc.perform(
            post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(trip3)));
        
        logger.info("Trip 3 created");

        logger.info("Retrieving trips from the repository");
        
        List<Trip> foundAll = repository.findTripsByDateAndOriginAndDestination(date, porto, lisboa);
        
        if (!foundAll.isEmpty())
            logger.info("We found " + foundAll.size() + " trips in the repository that go from - " 
                            + porto + " - to - " + lisboa + " - on - " + date);
        else
            logger.warn("No trips found in the repository that go from " + porto + " - to - " + lisboa + " - on - " + date);

        assertThat(foundAll).hasSize(2);

        logger.info("Test completed successfully.");
        logger.info("===========================================");
    }
}
