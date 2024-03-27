package ua.pt.Service;

import java.util.List;

import ua.pt.Domain.Reservation;
import ua.pt.Domain.Trip;

public interface ReservationService {
    public Reservation save(Reservation reservation);

    public Reservation getReservationById(Long id);

    public List<Reservation> getReservationsByTrip(Trip trip);

    public List<Reservation> getAllReservations();
}
