package ua.pt.Service.Impl;

import org.springframework.stereotype.Service;

import ua.pt.Repository.CityRepository;

@Service
public class CityServiceImpl {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }
}
