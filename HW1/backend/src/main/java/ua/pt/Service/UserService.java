package ua.pt.Service;

import java.util.List;

import ua.pt.Domain.Reservation;
import ua.pt.Domain.User;

public interface UserService {
    public User save(User user);

    public User getUserById(Long id);

    public List<User> getAllUsers();

    public void addReservation(Reservation reservation, User user);
}
