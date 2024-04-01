package ua.pt.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.pt.domain.Reservation;
import ua.pt.domain.Trip;
import ua.pt.domain.User;
import ua.pt.repository.ReservationRepository;
import ua.pt.repository.TripRepository;
import ua.pt.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;
    private final TripRepository tripRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TripRepository tripRepository){
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
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
    public List<Reservation> getReservationsByUser(User user){
        return reservationRepository.findAllByUser(user);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation save(Reservation reservation) {        
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservationStatus(Long id){
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        reservation.setStatus("paid");
        Trip trip = reservation.getTrip();
        trip.setOccupancy(trip.getOccupancy() + 1);
        tripRepository.save(trip);
        return reservationRepository.save(reservation);
    }
}
