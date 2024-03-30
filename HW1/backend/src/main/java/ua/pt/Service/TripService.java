package ua.pt.Service;

import java.util.List;

import ua.pt.Domain.Trip;

public interface TripService {
    public Trip save(Trip trip);

    public Trip getTripById(Long id);

    public List<Trip> getAllTrips();

    public List<Trip> getTripsByDateAndOriginAndDestination(String date, Long origin, Long destination, boolean all);
}