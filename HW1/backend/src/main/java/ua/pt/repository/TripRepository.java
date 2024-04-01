package ua.pt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.pt.domain.City;
import ua.pt.domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findTripsByDateAndOriginAndDestination(Date date, City origin, City destination);
}
