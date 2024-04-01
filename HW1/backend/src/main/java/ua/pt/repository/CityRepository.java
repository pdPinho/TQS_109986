package ua.pt.repository;

import org.springframework.stereotype.Repository;

import ua.pt.domain.City;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{
    City findByName(String name);
}
