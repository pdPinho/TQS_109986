package ua.pt.Service.Impl;

import org.springframework.stereotype.Service;

import ua.pt.Repository.UserRepository;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
