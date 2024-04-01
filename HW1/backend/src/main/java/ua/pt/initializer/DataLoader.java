package ua.pt.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ua.pt.domain.Bus;
import ua.pt.domain.City;
import ua.pt.domain.Trip;
import ua.pt.domain.User;
import ua.pt.repository.BusRepository;
import ua.pt.repository.CityRepository;
import ua.pt.repository.TripRepository;
import ua.pt.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final BusRepository busRepository;
    private final TripRepository tripRepository;

    public DataLoader(UserRepository userRepository, CityRepository cityRepository, BusRepository busRepository, TripRepository tripRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.busRepository = busRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
        loadCities();
        loadBuses();
        loadTrips();
    }

    private void loadUsers() {
        User user1 = new User("Pedro", "123123123");
        User user2 = new User("Novato", "123456789");

        userRepository.save(user1);
        userRepository.save(user2);
    }

    private void loadCities() {
        cityRepository.save(new City("Aveiro"));
        cityRepository.save(new City("Porto"));
        cityRepository.save(new City("Coimbra"));
        cityRepository.save(new City("Faro"));
    }

    private void loadBuses() {
        busRepository.save(new Bus("XPTO", 10));
        busRepository.save(new Bus("Autocarro Alberto", 1));
        busRepository.save(new Bus("FlixBus", 20));
    }

    private void loadTrips() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Whilst this date works correctly in the backend, frontend's calendar has a bug...
        Date date = dateFormat.parse("2024-04-20");

        City aveiro = cityRepository.findByName("Aveiro");
        City porto = cityRepository.findByName("Porto");
        City coimbra = cityRepository.findByName("Coimbra");
        City faro = cityRepository.findByName("Faro");

        Bus bus1 = busRepository.findByName("XPTO");
        Bus bus2 = busRepository.findByName("Autocarro Alberto");
        Bus bus3 = busRepository.findByName("FlixBus");

        tripRepository.save(new Trip(aveiro, coimbra, date, 5.20, bus1));
        tripRepository.save(new Trip(aveiro, coimbra, date, 7.20, bus2));
        tripRepository.save(new Trip(aveiro, coimbra, date, 9.25, bus3));
        tripRepository.save(new Trip(porto, coimbra, date, 15.00, bus2));
        tripRepository.save(new Trip(porto, faro, date, 12.25, bus3));
    }
}
