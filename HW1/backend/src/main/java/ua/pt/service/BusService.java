package ua.pt.service;

import java.util.List;

import ua.pt.domain.Bus;

public interface BusService {
    public Bus getBusById(Long id);

    public Bus getBusByName(String name);

    public List<Bus> getAllBuses();

    public Bus save(Bus bus);
}