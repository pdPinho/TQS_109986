package ua.pt.Service;

import java.util.Date;
import java.util.List;

import ua.pt.Domain.Bus;
import ua.pt.Domain.City;
import ua.pt.Domain.Trip;
import ua.pt.Domain.Reservation;

public interface TripService {
    public Trip save(City origin, City destination, Date date, double price, Bus bus);

    public Trip getTripById(Long id);

    public List<Trip> getTripsOnDate(Date date, City origin, City destination);

    public List<Reservation> getReservations();

    public int getOccupancy(Long id);

    public double getPrice();
}