package ua.pt.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.pt.Domain.Reservation;
import ua.pt.Domain.Trip;
import ua.pt.Repository.ReservationRepository;
import ua.pt.Service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> getReservationsByTrip(Trip trip) {
        return reservationRepository.findAllByTrip(trip);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation save(Reservation reservation) {
        reservation.getUser().getReservations().add(reservation);
        reservation.getTrip().getReservations().add(reservation);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservationStatus(Long id){
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        reservation.setStatus("paid");
        return reservationRepository.save(reservation);
    }
}
