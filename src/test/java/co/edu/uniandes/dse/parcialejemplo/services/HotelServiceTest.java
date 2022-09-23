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

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HotelService.class)
class HotelServiceTest {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();

    private List<HotelEntity> hoteles = new ArrayList<>();

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
            hoteles.add(hotel);
        }
    }

    @Test
    void testCreateHotelesValid() throws IllegalOperationException {
        HotelEntity entity, newEntity;
        List<HotelEntity> list1 = new ArrayList<>();
        List<HotelEntity> list2 = new ArrayList<>();
        entity = factory.manufacturePojo(HotelEntity.class);
        newEntity = factory.manufacturePojo(HotelEntity.class);
        list1.add(entity); list1.add(newEntity);
        list2 = hotelService.createHoteles(list1);
    }

    @Test
    void testCreateHotelesInvalid() {
        assertThrows(IllegalOperationException.class, () -> {
            String name = "Generico";
            List<HotelEntity> list = new ArrayList<>();
            HotelEntity pojo1 = factory.manufacturePojo(HotelEntity.class);
            HotelEntity pojo2 = factory.manufacturePojo(HotelEntity.class);

            pojo1.setName(name); pojo2.setName(name);
            list.add(pojo1); list.add(pojo2);

            hotelService.createHoteles(list);
        });

    }
}
