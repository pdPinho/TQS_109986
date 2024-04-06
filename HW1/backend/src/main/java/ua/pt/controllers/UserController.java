package ua.pt.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ua.pt.domain.User;
import ua.pt.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode="201", description="User created")
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        HttpStatus status = HttpStatus.CREATED;
        User saved = userService.save(user);
        return new ResponseEntity<>(saved, status);
    }

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode="200", description="Retrieved list of users")
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode="200", description="Retrieved user by ID")
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
}