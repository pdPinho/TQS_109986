package ua.pt.CarApp;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class B_CarService {
    @Mock( lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    private Car car;

    @BeforeEach
    void setUp(){
        car = new Car("Something", "ferrari");
        car.setCarId((long)111);
    }

    @Test
    void carSavedIsSaved(){
     when(carRepository.save(car)).thenReturn(car);
 
     carManagerService.save(car);
 
     verify(carRepository, times(1)).save(car);
    }

    @Test
     void carFoundIfExists() {
        String model = "ferrari";
        Optional<Car> found = carManagerService.getCarDetails(car.getCarId());

        assertThat(found.isPresent()).isTrue();

        found.ifPresent(car -> assertThat(car.getModel()).isEqualTo(model));
        verify(carRepository, times(1)).findById(car.getCarId());
    }

    @Test
    void carNotFoundIfNotExists() {
        long id = 112;
       Optional<Car> fromDb = carManagerService.getCarDetails(id);
       assertThat(fromDb).isEmpty();

       verify(carRepository, times(1)).findById(id);
   }

   @Test
   void findAllCarsReturnsCars(){
    Car newCar = new Car("maaarker", "mooodel");
    List<Car> cars = Arrays.asList(car, newCar);

    when(carRepository.findAll()).thenReturn(cars);

    List<Car> carsFound = carManagerService.getAllCars();
    verify(carRepository, times(1)).findAll();

    assertThat(carsFound).hasSize(2);
   }
}
