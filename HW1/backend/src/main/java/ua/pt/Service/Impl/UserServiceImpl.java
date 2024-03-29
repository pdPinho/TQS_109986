package ua.pt.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.pt.Domain.User;
import ua.pt.Repository.UserRepository;
import ua.pt.Service.UserService;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
