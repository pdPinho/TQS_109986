package ua.pt.service;

import java.util.List;

import ua.pt.domain.User;

public interface UserService {
    public User save(User user);

    public User getUserById(Long id);

    public List<User> getAllUsers();
}
