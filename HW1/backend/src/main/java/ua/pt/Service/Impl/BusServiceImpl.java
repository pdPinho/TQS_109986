package ua.pt.Service.Impl;

import org.springframework.stereotype.Service;

import ua.pt.Repository.BusRepository;
import ua.pt.Service.BusService;

@Service
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;

    public BusServiceImpl(BusRepository busRepository){
        this.busRepository = busRepository;
    }
}
