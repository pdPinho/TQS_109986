package ua.pt.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long busId;

    private String name;

    private int capacity;

    private int currentCapacity;

    public Bus(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
    }
}
