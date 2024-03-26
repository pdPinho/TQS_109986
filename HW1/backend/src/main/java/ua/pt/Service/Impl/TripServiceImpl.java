package ua.pt.Service.Impl;

import org.springframework.stereotype.Service;

import ua.pt.Repository.TripRepository;
import ua.pt.Service.TripService;

@Service
public class TripServiceImpl {
    private final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository){
        this.tripRepository = tripRepository;
    }
}
