package ua.pt.Service;

import ua.pt.Domain.Reservation;

public interface ReservationService {
    public Reservation save(Reservation reservation);

    public Reservation getReservationById(Long id);

    
}
