package ua.pt.Service;

import java.util.List;

import ua.pt.Domain.Reservation;
import ua.pt.Domain.Trip;
import ua.pt.Domain.User;

public interface ReservationService {
    public Reservation save(Reservation reservation);

    public Reservation getReservationById(Long id);

    public List<Reservation> getReservationsByTrip(Trip trip);

    public List<Reservation> getReservationsByUser(User user);

    public List<Reservation> getAllReservations();

    public Reservation updateReservationStatus(Long id);
}
