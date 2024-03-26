package ua.pt.Service.Impl;

import org.springframework.stereotype.Service;

import ua.pt.Repository.ReservationRepository;

@Service
public class ReservationServiceImpl {
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }
}
