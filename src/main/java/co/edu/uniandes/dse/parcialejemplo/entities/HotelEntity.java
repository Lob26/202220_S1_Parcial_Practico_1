package co.edu.uniandes.dse.parcialejemplo.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter @Setter @Entity
public class HotelEntity extends BaseEntity{
    private String name;
    private String direction;
    private int numStars;

    @PodamExclude
    @OneToMany(mappedBy = "hotel")
    private List<HabitacionEntity> habitaciones;
}
