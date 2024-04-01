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

import ua.pt.BusApplication;
import ua.pt.domain.Bus;
import ua.pt.domain.City;
import ua.pt.domain.Reservation;
import ua.pt.domain.Trip;
import ua.pt.domain.User;
import ua.pt.repository.BusRepository;
import ua.pt.repository.CityRepository;
import ua.pt.repository.ReservationRepository;
import ua.pt.repository.TripRepository;
import ua.pt.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = BusApplication.class, properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class ReservationControllerITest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private TripRepository tripRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private BusRepository busRepository;

    private final Logger logger = LoggerFactory.getLogger(TripControllerITest.class);

    @AfterEach
    public void resetInit(){
        repository.deleteAll();
        userRepository.deleteAll();
        tripRepository.deleteAll();
        cityRepository.deleteAll();
        busRepository.deleteAll();
    }

    @Test
    void givenManyReservationsByUser_whenGetByUser_ReturnReservations() throws IOException, Exception {
        logger.info("===========================================");
        logger.info("Starting test givenManyReservationsByUser_whenGetByUser_ReturnReservations...");
        
        City porto = new City("Porto");
        City lisboa = new City("Lisboa");
        User user = new User("Pedro", "123123123");
        Bus bus = new Bus("XPTO", 10);

        busRepository.save(bus);
        userRepository.save(user);
        cityRepository.save(porto);
        cityRepository.save(lisboa);

        Trip trip = new Trip(porto, lisboa, 
                            new Date(2024-04-01), 2.5, bus);
        Trip trip2 = new Trip(porto, lisboa, 
                            new Date(2024-04-01), 2.5, bus);


        tripRepository.save(trip);
        tripRepository.save(trip2);

        Reservation reservation = new Reservation("awaiting-payment", "EUR", 
                                                    trip, user, 5.2);
        Reservation reservation2 = new Reservation("awaiting-payment", "EUR", 
                                                    trip, user, 5.2);
        Reservation reservation3 = new Reservation("awaiting-payment", "EUR", 
                                                    trip2, user, 5.2);

        
        logger.info("POST requests sent to create reservations");
        mvc.perform(
            post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)));

        logger.info("Reservation 1 created");

        mvc.perform(
            post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation2)));

        logger.info("Reservation 2 created");

        mvc.perform(
            post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation3)));

        logger.info("Reservation 3 created");

        logger.info("Retrieving reservations based on user from the repository");

        List<Reservation> foundUserReservations = repository.findAllByUser(user);

        if (!foundUserReservations.isEmpty())
            logger.info("We found " + foundUserReservations.size() + " reservations in the repository that belong to - " + user);
        else
            logger.warn("No reservations found in the repository that belong to " + user);

        assertThat(foundUserReservations).hasSize(3);

        logger.info("Test completed successfully.");
        logger.info("===========================================");
    }

    @Test
    void givenManyReservationsByTrip_whenGetByTrip_ReturnReservations() throws IOException, Exception {
        logger.info("===========================================");
        logger.info("Starting test givenManyReservationsByTrip_whenGetByTrip_ReturnReservations...");
        
        City porto = new City("Porto");
        City lisboa = new City("Lisboa");
        User user = new User("Pedro", "123123123");
        User user2 = new User("Test", "123123123");
        Bus bus = new Bus("XPTO", 10);

        busRepository.save(bus);
        userRepository.save(user);
        userRepository.save(user2);
        cityRepository.save(porto);
        cityRepository.save(lisboa);

        Trip trip = new Trip(porto, lisboa, 
                            new Date(2024-04-01), 2.5, bus);

        tripRepository.save(trip);

        Reservation reservation = new Reservation("awaiting-payment", "EUR", 
                                                    trip, user, 5.2);
        Reservation reservation2 = new Reservation("awaiting-payment", "EUR", 
                                                    trip, user2, 5.2);

        
        logger.info("POST requests sent to create reservations");
        mvc.perform(
            post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)));

        logger.info("Reservation 1 created");

        mvc.perform(
            post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation2)));

        logger.info("Reservation 2 created");

        logger.info("Retrieving reservations based on trip from the repository");

        List<Reservation> foundTripReservations = repository.findAllByTrip(trip);

        if (!foundTripReservations.isEmpty())
            logger.info("We found " + foundTripReservations.size() + " reservations in the repository that belong to - " + trip);
        else
            logger.warn("No reservations found in the repository that belong to " + trip);

        assertThat(foundTripReservations).hasSize(2);

        logger.info("Test completed successfully.");
        logger.info("===========================================");
    }

    @Test
    void whenPostReservation_saveReservation() throws IOException, Exception {
        logger.info("===========================================");
        logger.info("Starting test whenPostReservation_saveReservation...");
        
        City porto = new City("Porto");
        City lisboa = new City("Lisboa");
        User user = new User("Pedro", "123123123");
        Bus bus = new Bus("XPTO", 10);

        busRepository.save(bus);
        userRepository.save(user);
        cityRepository.save(porto);
        cityRepository.save(lisboa);

        Trip trip = new Trip(porto, lisboa, 
                            new Date(2024-04-01), 2.5, bus);

        tripRepository.save(trip);

        Reservation reservation = new Reservation("awaiting-payment", "EUR", 
                                                    trip, user, 5.2);

        
        logger.info("POST requests sent to create reservation");
        mvc.perform(
            post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)));

        logger.info("Retrieving reservation from the repository");

        List<Reservation> found = repository.findAll();

        if (!found.isEmpty())
            logger.info("We found " + found.size() + " reservation in the repository");
        else
            logger.warn("No reservations found in the repository");

        assertThat(found).hasSize(1);

        logger.info("Test completed successfully.");
        logger.info("===========================================");
    }


    @Test
    void whenPostReservationPayment_updateReservation() throws IOException, Exception {
        logger.info("===========================================");
        logger.info("Starting test whenPostReservationPayment_updateReservation...");
        
        City porto = new City("Porto");
        City lisboa = new City("Lisboa");
        User user = new User("Pedro", "123123123");
        Bus bus = new Bus("XPTO", 10);

        busRepository.save(bus);
        userRepository.save(user);
        cityRepository.save(porto);
        cityRepository.save(lisboa);

        Trip trip = new Trip(porto, lisboa, 
                            new Date(2024-04-01), 2.5, bus);

        tripRepository.save(trip);

        Reservation reservation = new Reservation("awaiting-payment", "EUR", 
                                                    trip, user, 5.2);

        
        logger.info("POST request sent to create reservation");
        mvc.perform(
            post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)));

        logger.info("Retrieving reservation from the repository");

        Reservation found = repository.findAll().get(0);

        found.setStatus("paid");

        logger.info("POST request sent to update reservation");
        mvc.perform(
            post("/api/reservations/{id}/pay", found.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(found)));

        logger.info("Retrieving updated reservation from the repository");

        found = repository.findById(found.getId()).get();

        assertThat(found.getStatus()).isEqualTo("paid");

        logger.info("Test completed successfully.");
        logger.info("===========================================");
    }
}
