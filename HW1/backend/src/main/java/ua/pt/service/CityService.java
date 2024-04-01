package ua.pt.service;

import java.util.List;

import ua.pt.domain.City;

public interface CityService {
    public City save(City city);

    public City getCityById(Long id);

    public City getCityByName(String name);

    public List<City> getAllCities();
}