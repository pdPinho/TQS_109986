package ua.pt.service;

import java.util.List;

import ua.pt.domain.Trip;

public interface TripService {
    public Trip save(Trip trip);

    public Trip getTripById(Long id);

    public List<Trip> getAllTrips();

    public List<Trip> getTripsByDateAndOriginAndDestination(String date, Long origin, Long destination, boolean all);
}