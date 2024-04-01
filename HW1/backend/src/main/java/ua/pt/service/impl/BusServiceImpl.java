package ua.pt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.pt.domain.Bus;
import ua.pt.repository.BusRepository;
import ua.pt.service.BusService;

@Service
public class BusServiceImpl implements BusService{
    private final BusRepository busRepository;

    @Autowired
    public BusServiceImpl(BusRepository busRepository){
        this.busRepository = busRepository;
    }

    @Override
    public Bus getBusById(Long id){
        return busRepository.findById(id).orElse(null);
    }

    @Override
    public Bus getBusByName(String name){
        return busRepository.findByName(name);
    }

    @Override
    public Bus save(Bus bus){
        return busRepository.save(bus);
    }

    @Override
    public List<Bus> getAllBuses(){
        return busRepository.findAll();
    }
}
