package ua.pt.service;

import java.util.List;

import ua.pt.domain.Reservation;
import ua.pt.domain.Trip;
import ua.pt.domain.User;

public interface ReservationService {
    public Reservation save(Reservation reservation);

    public Reservation getReservationById(Long id);

    public List<Reservation> getReservationsByTrip(Trip trip);

    public List<Reservation> getReservationsByUser(User user);

    public List<Reservation> getAllReservations();

    public Reservation updateReservationStatus(Long id);
}
