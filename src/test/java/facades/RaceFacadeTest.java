package facades;
import dtos.RaceDTO;
import entities.Car;
import entities.Race;
import entities.User;
import errorhandling.EntityNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class RaceFacadeTest {

    private static EntityManagerFactory emf;
    private static RaceFacade facade;
    Race race;
    Race race2;
    Car car;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RaceFacade.getRaceFacade(emf);


    }

    // Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    @AfterAll
    public static void tearDownClass() {
        // emf.close();
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Timestamp ts = Timestamp.valueOf("22 Jan, 2023 1:00:00 AM");
            race = new Race("aha", "dasd", ts, 2);
            race2 = new Race("Radd", "dawf", ts, 1);
            car = new Car("dqw", "dqw", "dqw", "2222", "dwq", "rdwq");
            race2.addCar(car);
            em.persist(race);
            em.persist(race2);
            em.persist(car);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void create() {
        Timestamp ts = Timestamp.valueOf("22 Jan, 2023 1:00:00 AM");
        Race expected = new Race("disco fortnite race", "LA", ts, 2);
        Race actual = facade.test(expected);

        assertEquals(expected, actual);
    }

    @Test
    void getCount() {
        long actual = facade.getCount();
        assertEquals(35, actual);
    }
}