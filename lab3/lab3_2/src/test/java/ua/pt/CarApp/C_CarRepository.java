package ua.pt.CarApp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class C_CarRepository {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindCarById_thenReturnCar() {
        Car car = new Car("maker","model");

        entityManager.persistAndFlush(car);

        Optional<Car> found = carRepository.findById(car.getCarId());
        assertThat(found.get()).isEqualTo(car);
    }

    @Test
    void whenFindCarByIdNotExists_thenReturnNoExists() {
        Optional<Car> found = carRepository.findById((long)0);
        assertThat(found).isEmpty();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars(){
        Car car1 = new Car("maker-1", "model-1");
        Car car2 = new Car("Maker-2", "Model-2");

        entityManager.persist(car1);
        entityManager.persistAndFlush(car2);

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(2).extracting(Car::getMaker).containsOnly(car1.getMaker(), car2.getMaker());
    }
}
