package ua.pt.repository;

import org.springframework.stereotype.Repository;

import ua.pt.domain.Reservation;
import ua.pt.domain.Trip;
import ua.pt.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    List<Reservation> findAllByTrip(Trip trip);
    List<Reservation> findAllByUser(User user);
}
