package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.DriverFacade;
import facades.RaceFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("driver")
public class DriverResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final DriverFacade FACADE = DriverFacade.getDriverFacade(EMF_Creator.createEntityManagerFactory());
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("car/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllDriversByCarID(@PathParam("id") int id) {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getDriversByCarID(id)))
                .build();
    }
}
