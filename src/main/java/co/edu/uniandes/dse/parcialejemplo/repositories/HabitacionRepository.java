package co.edu.uniandes.dse.parcialejemplo.repositories;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitacionRepository extends JpaRepository<HabitacionEntity, Long> {
    List<HabitacionEntity> findByIdNumRoom(int numRoom);
}
