package ua.pt.Domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int seat;

    private boolean classe_type;

    @OneToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public Reservation(int seat, boolean classe_type, Trip trip){
        this.seat = seat;
        this.classe_type = classe_type;
        this.trip = trip;
    }
}