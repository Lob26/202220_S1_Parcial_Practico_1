package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class HabitacionService {
    @Autowired
    HabitacionRepository habitacionRepository;

    @Transactional
    public HabitacionEntity createHabitacion(HabitacionEntity habitacion) throws IllegalOperationException {
        log.info("Inicia el proceso de creacion de una habitacion");

        if (habitacion.getNumPeople() < 2*habitacion.getNumBed())
            throw new IllegalOperationException("La cantidad de gente es mayor al limite aceptado");

        log.info("Finaliza el proceso de creacion de una habitacion");
        return habitacionRepository.save(habitacion);
    }
}
