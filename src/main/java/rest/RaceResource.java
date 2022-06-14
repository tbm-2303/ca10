package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RaceDTO;
import entities.Race;
import facades.RaceFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("race")
public class RaceResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final RaceFacade FACADE = RaceFacade.getRaceFacade(EMF_Creator.createEntityManagerFactory());
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Path("populate")
    @Produces({MediaType.APPLICATION_JSON})
    public String populatel() {
        EntityManager em = EMF.createEntityManager();
        //create some races
        em.getTransaction().begin();
        //make relations
        //persist ->  em.persist(?);
        em.getTransaction().commit();
        return "{\"msg\":\"setup all good\"}";
    }

    @GET
    @Path("getall")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<Race> list = FACADE.getAll();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        List<RaceDTO> raceDTOS = new ArrayList<>();

        for (Race race : list) {
            if (race.getDate().after((Date) ts)){
                raceDTOS.add(new RaceDTO(race));
            }
        }
        return Response
                .ok()
                .entity(GSON.toJson(raceDTOS))
                .build();
    }


    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("create")
    public String createRace(String race){
        RaceDTO raceDTO = GSON.fromJson(race, RaceDTO.class);
        RaceDTO createdRace = FACADE.createRace(raceDTO);
        return GSON.toJson(createdRace);
    }
}
