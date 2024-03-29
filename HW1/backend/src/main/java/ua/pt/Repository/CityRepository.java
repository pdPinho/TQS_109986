package ua.pt.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import ua.pt.Domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{
    City findByName(String name);
}
