package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;

    @Transactional
    public List<HotelEntity> createHoteles(List<HotelEntity> hoteles) throws IllegalOperationException {
        log.info("Inicia el proceso de creacion de hoteles");

        for (HotelEntity hotel1 : hoteles) for (HotelEntity hotel2 : hoteles) {
            if (hotel1.getName().equalsIgnoreCase(hotel2.getName()))
                throw new IllegalOperationException("El nombre " + hotel1.getName() + "esta repetido");
        }

        log.info("Finaliza el proceso de creacion de hoteles");
        return hotelRepository.saveAll(hoteles);
    }
}
