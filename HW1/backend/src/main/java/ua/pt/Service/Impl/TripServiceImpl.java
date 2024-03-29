package ua.pt.Service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.pt.Domain.City;
import ua.pt.Domain.Reservation;
import ua.pt.Domain.Trip;
import ua.pt.Repository.CityRepository;
import ua.pt.Repository.TripRepository;
import ua.pt.Service.TripService;

@Service
public class TripServiceImpl implements TripService{
    private final TripRepository tripRepository;
    private final CityRepository cityRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, CityRepository cityRepository){
        this.tripRepository = tripRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public List<Trip> getTripsByDateAndOriginAndDestination(String date_string, Long origin_id, Long destination_id) {
        City origin = cityRepository.findById(origin_id).orElse(null);
        City destination = cityRepository.findById(destination_id).orElse(null);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(date_string);
            return tripRepository.findTripsByDateAndOriginAndDestination(date, origin, destination);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void addReservation(Reservation reservation, Trip trip){
        trip.getReservations().add(reservation);
        tripRepository.save(trip);
    }

    /*
    @Override
    public boolean isFull(Long id){
        Trip trip = tripRepository.findById(id).orElse(null);
        if (trip.getOccupancy() == trip.getBus().getCapacity())
            return true;
        return false;
    }
    */
}
