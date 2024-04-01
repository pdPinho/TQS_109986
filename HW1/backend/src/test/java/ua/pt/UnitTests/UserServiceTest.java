package ua.pt.UnitTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.pt.domain.User;
import ua.pt.repository.UserRepository;
import ua.pt.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock(lenient = true)
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp(){
        User user1 = new User("Pedro", "123123123");
        user1.setId((long)111);

        User user2 = new User("Outro", "123123123");

        List<User> allUsers = Arrays.asList(user1, user2);

        Mockito.when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
    }

    @Test
    void whenSaveUser_thenSaveUser() {
        User user = new User("Pedro", "123123123");
        userServiceImpl.save(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void whenFindUserById_thenFoundIfExists(){
        long id = 111;
        User user = userServiceImpl.getUserById(id);

        assertThat(user.getId()).isEqualTo(id);

        verify(userRepository, times(1)).findById(user.getId());
    }
    @Test
    void whenInvalidId_thenShouldNotBeFound(){
        User user = userServiceImpl.getUserById(-99L);

        assertThat(user).isNull();

        verify(userRepository, times(1)).findById(-99L);
    }

    @Test
    void givenManyUsers_whenGetAll_thenReturnAll() {
        List<User> allUsers = userServiceImpl.getAllUsers();

        assertThat(allUsers).hasSize(2);

        verify(userRepository, times(1)).findAll();
    }

}
