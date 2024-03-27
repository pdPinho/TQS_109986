package ua.pt.IntegrationTests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ua.pt.Controllers.UserController;
import ua.pt.Service.UserService;
import ua.pt.Domain.Bus;
import ua.pt.Domain.City;
import ua.pt.Domain.Reservation;
import ua.pt.Domain.Trip;
import ua.pt.Domain.User;


@WebMvcTest(UserController.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @BeforeEach
    public void setUp() throws Exception {}

    @Test
    void whenPostUser_thenCreateUser() throws Exception {
        User user = new User("Pedro", "123123123");

        when(service.save(Mockito.any())).thenReturn(user);

        mvc.perform(
            post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(user)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is("Pedro")));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        User user1 = new User("Pedro", "123123123");
        User user2 = new User("Outro", "321321321");
        User user3 = new User("Novo", "123456789");

        List<User> allUsers = Arrays.asList(user1, user2, user3);

        when(service.getAllUsers()).thenReturn(allUsers);

        mvc.perform(
            get("/api/users")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].name", is(user1.getName())))
            .andExpect(jsonPath("$[1].name", is(user2.getName())))
            .andExpect(jsonPath("$[2].name", is(user3.getName())));

        verify(service, times(1)).getAllUsers();
    }

    @Test
    void givenUser_whenGetById_thenReturnUser() throws Exception{
        User user = new User("Pedro", "123123123");
        user.setId((long)0);

        when(service.getUserById((long)0)).thenReturn(user);

        mvc.perform(
            get("/api/users/0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(0)));

        verify(service, times(1)).getUserById((long)0);
    }

    @Test
    void givenUserWithReservations_whenGetReservations_thenReturnAllUserReservations() throws Exception {
        User user = new User("Pedro", "123123123");
        user.setId((long)0);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1, 
                                            true, 
                                            new Trip(
                                                new City("Porto"), 
                                                new City("Aveiro"),
                                                new Date(), 
                                                2.5, 
                                                new Bus("XPTO", 20))));
        
        user.setReservations(reservations);

        when(service.getAllReservations((long)0)).thenReturn(reservations);

        mvc.perform(
            get("/api/users/0/reservations")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].classe", is(true)));

        verify(service, times(1)).getAllReservations((long)0);
    }

    @Test
    void whenGetByInvalidId_thenReturnError() throws Exception {
        mvc.perform(
            get("/api/users/999")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(service, times(1)).getUserById((long)999);
    }


    /*
    @Test
    void whenAddReservation_thenReturnUpdatedUser() throws Exception {
        User user = new User("Pedro", "123123123");
        user.setId((long)0);
        Reservation reservation = new Reservation();

        when(service.getUserById((long)0)).thenReturn(user);

        mvc.perform(
            post("/api/users/0/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(0)));

        verify(service, times(1)).getUserById((long)0);
        verify(service, times(1)).addReservation(reservation, user);
    }
     */
}
