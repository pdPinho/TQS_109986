package ua.pt.Service;

import java.util.List;

import ua.pt.Domain.City;

public interface CityService {
    public City save(City city);

    public City getCityById(Long id);

    public City getCityByName(String name);

    public List<City> getAllCities();
}