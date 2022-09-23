package co.edu.uniandes.dse.parcialejemplo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter @Setter @Entity
public class HabitacionEntity extends BaseEntity {
    private int idNumRoom;
    private int numPeople;
    private int numBed;
    private int numBath;

    @ManyToOne
    private HotelEntity hotel;
}
