package ua.pt.Repository;

import org.springframework.stereotype.Repository;

import ua.pt.Domain.Reservation;
import ua.pt.Domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    List<Reservation> findAllByReservations(User user);
}
