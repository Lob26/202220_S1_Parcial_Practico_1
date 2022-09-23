package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HotelHabitacionService.class)
class HotelHabitacionServiceTest {
    @Autowired
    private HotelHabitacionService hotHabService;
    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();

    private HotelEntity hotelEntity;
    private HabitacionEntity habitacionEntity;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM HotelEntity");
        entityManager.getEntityManager().createQuery("DELETE FROM HabitacionEntity");
    }

    private void insertData() {
        HotelEntity hotel = factory.manufacturePojo(HotelEntity.class);
        HabitacionEntity habitacion = factory.manufacturePojo(HabitacionEntity.class);

        entityManager.persist(hotel); entityManager.persist(habitacion);

        hotel.getHabitaciones().add(habitacion);
        habitacion.setHotel(hotel);

        hotelEntity = hotel;
        habitacionEntity = habitacion;
    }

    @Test
    void addHabitacionValid() throws EntityNotFoundException {
        HotelEntity hotel;
        HabitacionEntity habitacion, lastHabitacion;

        hotel = factory.manufacturePojo(HotelEntity.class);
        entityManager.persist(hotel);

        habitacion = factory.manufacturePojo(HabitacionEntity.class);
        entityManager.persist(habitacion);

        lastHabitacion = hotHabService.addHabitacion(hotel.getId(), habitacion.getId());

        assertEquals(habitacion.getId(), lastHabitacion.getId());
        assertEquals(habitacion.getIdNumRoom(), lastHabitacion.getIdNumRoom());
        assertEquals(habitacion.getHotel(), lastHabitacion.getHotel());
        assertEquals(habitacion.getNumBed(), lastHabitacion.getNumBed());
        assertEquals(habitacion.getNumBath(), lastHabitacion.getNumBath());
        assertEquals(habitacion.getNumPeople(), lastHabitacion.getNumPeople());
    }

    @Test
    void addHabitacionInvalid1Hotel() {
        assertThrows(EntityNotFoundException.class, () -> {
            HotelEntity entity = factory.manufacturePojo(HotelEntity.class);
            entityManager.persist(entity);
            hotHabService.addHabitacion(entity.getId(), 0L);
        });
    }

    @Test
    void addHabitacionInvalid1Habitacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            HabitacionEntity entity = factory.manufacturePojo(HabitacionEntity.class);
            entityManager.persist(entity);
            hotHabService.addHabitacion(0L, entity.getId());
        });
    }
}