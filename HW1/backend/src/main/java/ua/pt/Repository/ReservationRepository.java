package ua.pt.Repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.pt.Domain.Reservation;
import ua.pt.Domain.Trip;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    List<Reservation> findAllByTrip(Trip trip);
}
