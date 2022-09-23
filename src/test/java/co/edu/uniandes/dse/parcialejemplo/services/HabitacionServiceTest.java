package co.edu.uniandes.dse.parcialejemplo.services;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
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

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HabitacionService.class)
class HabitacionServiceTest {
    @Autowired
    private HabitacionService habitacionService;
    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();

    private List<HabitacionEntity> habitaciones = new ArrayList<>();

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
        for (int i = 0; i < 3; i++) {
            HotelEntity hotel = factory.manufacturePojo(HotelEntity.class);
            HabitacionEntity habitacion = factory.manufacturePojo(HabitacionEntity.class);
            entityManager.persist(hotel); entityManager.persist(habitacion);

            hotel.getHabitaciones().add(habitacion);
            habitacion.setHotel(hotel);
            habitaciones.add(habitacion);
        }
    }

    @Test
    void testCreateHabitacionValid() throws IllegalOperationException {
        HabitacionEntity entity, result, temp;
        entity = factory.manufacturePojo(HabitacionEntity.class);
        temp = habitacionService.createHabitacion(entity);
        assertNotNull(temp);
        result = entityManager.find(HabitacionEntity.class, temp.getId());

        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getIdNumRoom(), result.getIdNumRoom());
        assertEquals(entity.getHotel(), result.getHotel());
        assertEquals(entity.getNumBed(), result.getNumBed());
        assertEquals(entity.getNumBath(), result.getNumBath());
        assertEquals(entity.getNumPeople(), result.getNumPeople());
    }

    @Test
    void testCreateHabitacionInvalid() {
        assertThrows(IllegalOperationException.class, () -> {
            int value = 5;
            HabitacionEntity entity = factory.manufacturePojo(HabitacionEntity.class);
            entity.setNumBed(2*value);
            entity.setNumPeople(value);
            habitacionService.createHabitacion(entity);
        });
    }
}
