package ua.pt.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.pt.Domain.Reservation;
import ua.pt.Domain.User;
import ua.pt.Service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        HttpStatus status = HttpStatus.CREATED;
        User saved = userService.save(user);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        User user = userService.getUserById(id);
        HttpStatus status;
        if (user != null)
            status = HttpStatus.OK;
        else
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(user, status);
    }

    @GetMapping("/users/{id}/reservations")
    public List<Reservation> getAllUserReservations(@PathVariable long id){
        return userService.getAllReservations(id);
    }

    @PostMapping("/users/{id}/reservations")
    public ResponseEntity<User> addReservation(@PathVariable long id, @RequestBody Reservation reservation){
        HttpStatus status = HttpStatus.CREATED;
        User user = userService.getUserById(id);
        userService.addReservation(reservation, user);
        return new ResponseEntity<>(user, status);
    }
}