package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class HotelHabitacionService {
    @Autowired
    HabitacionRepository habitacionRepository;
    @Autowired
    HotelRepository hotelRepository;

    @Transactional
    public HabitacionEntity addHabitacion(Long idHotel, Long idHabitacion) throws EntityNotFoundException {
        log.info("Inicia el proceso de aniadir una habitacion a un hotel");

        Optional<HotelEntity> hotel = hotelRepository.findById(idHotel);
        if (hotel.isEmpty()) throw new EntityNotFoundException("El hotel con id " + idHotel + "no existe");
        Optional<HabitacionEntity> habitacion = habitacionRepository.findById(idHabitacion);
        if (habitacion.isEmpty())
            throw new EntityNotFoundException("La habitacion con id " + idHabitacion + "no existe");

        habitacion.get().setHotel(hotel.get());
        log.info("Finaliza el proceso de aniadir una habitacion a un hotel");
        return habitacion.get();
    }
}
