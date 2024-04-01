package ua.pt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.pt.domain.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long>{
    public Bus findByName(String name);
}
