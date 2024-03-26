package ua.pt.Service;

import ua.pt.Domain.City;

public interface CityService {
    public City save(String name);

    public City getCityById(Long id);

    public City getCityByName(String name);
}
