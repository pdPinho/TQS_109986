package ua.pt.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.pt.Domain.City;
import ua.pt.Repository.CityRepository;
import ua.pt.Service.CityService;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    @Override
    public City getCityByName(String name) {
        return cityRepository.findByName(name);
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }
}
