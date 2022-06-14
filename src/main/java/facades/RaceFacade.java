package facades;

import dtos.RaceDTO;
import entities.Car;
import entities.Driver;
import entities.Race;
import entities.User;
import errorhandling.EntityNotFoundException;
import javassist.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class RaceFacade {
    private static RaceFacade instance;
    private static EntityManagerFactory emf;


    private RaceFacade() {
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static RaceFacade getRaceFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RaceFacade();
        }
        return instance;
    }

    public RaceDTO createRace(RaceDTO raceDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Race race = new Race(raceDTO.getName(), raceDTO.getLocation(), raceDTO.getDate(), raceDTO.getDuration());
            em.getTransaction().begin();
            em.persist(race);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return raceDTO;
    }


    public Race getById(int id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        Race race = em.find(Race.class, id);
        if (race == null)
            throw new EntityNotFoundException("Race with ID: " + id + " was not found");
        return race;
    }

    public List<Race> getAll() {
        EntityManager em = getEntityManager();
        TypedQuery<Race> query = em.createQuery("SELECT r FROM Race r", Race.class);
        return query.getResultList();
    }


    public long getCount() {
        EntityManager em = getEntityManager();
        try {
            return (long) em.createQuery("SELECT COUNT(r) FROM Race r").getSingleResult();
        } finally {
            em.close();
        }
    }


}
