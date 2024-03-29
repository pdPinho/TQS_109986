package ua.pt.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.pt.Domain.City;
import ua.pt.Domain.Reservation;
import ua.pt.Domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Reservation> findAllByReservations(Trip trip);
    List<Trip> findTripsByDateAndOriginAndDestination(Date date, City origin, City destination);
}
