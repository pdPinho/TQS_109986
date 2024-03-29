package ua.pt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.pt.Domain.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long>{
    public Bus findByName(String name);
}
