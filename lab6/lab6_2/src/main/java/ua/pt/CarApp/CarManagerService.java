package ua.pt.CarApp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarManagerService {
    CarRepository carRepository;

    @Autowired
    public CarManagerService(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    Car save(Car car){
        return carRepository.save(car);
    }

    List<Car> getAllCars(){
        return carRepository.findAll();
    }

    Optional<Car> getCarDetails(long id){
        return carRepository.findById(id);
    }
}
