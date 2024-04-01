package ua.pt.UnitTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ua.pt.domain.User;
import ua.pt.repository.UserRepository;

@DataJpaTest
class UserRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindUserById_thenReturnUser() {
        User user = new User("Pedro", "123123123");

        entityManager.persistAndFlush(user);

        User found = userRepository.findById(user.getId()).get();
        assertThat(found).isEqualTo(user);
    }

    @Test
    void whenFindUserByInvalidId_thenReturnNoExists() {
        Optional<User> found = userRepository.findById(1L);
        assertThat(found).isEmpty();
    }

    @Test
    void whenFindUserByName_thenReturnUser(){
        User user = new User("Pedro", "123123123");

        entityManager.persistAndFlush(user);

        User found = userRepository.findByName("Pedro");
        assertThat(found).isEqualTo(user);
    }

    @Test
    void whenFindUserByInvalidName_thenReturnNoExists() {
        User found = userRepository.findByName("XPTO");
        assertThat(found).isNull();
    }

    @Test
    void givenSetOfUsers_whenFindAll_thenReturnAllUsers() {
        User user = new User("Pedro", "123123123");
        User user2 = new User("Joaquim", "123123123");

        entityManager.persist(user);
        entityManager.persistAndFlush(user2);

        List<User> allUsers = userRepository.findAll();

        assertThat(allUsers).hasSize(2)
                            .extracting(User::getName).containsOnly(user.getName(), user2.getName());
    }
}
