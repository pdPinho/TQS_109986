package ua.pt.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private double price;

    private int occupancy;

    @ManyToOne
    @JoinColumn(name = "origin_id", nullable = false)
    private City origin;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private City destination;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    public Trip(City origin, City destination, Date date, double price, Bus bus){
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.price = price;
        this.bus = bus;
        this.occupancy = 0;
    }
}
