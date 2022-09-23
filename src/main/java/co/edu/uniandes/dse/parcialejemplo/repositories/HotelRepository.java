package co.edu.uniandes.dse.parcialejemplo.repositories;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    List<HotelRepository> findHotelEntitiesByName(String nane);
}
