package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.auth.AuthorizationFilter;
import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IConstrasenaLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.ContrasenaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import com.sun.istack.logging.Logger;

@Path("contrasena")
@Secured({AuthorizationFilter.Role.propietario})
@Produces(MediaType.APPLICATION_JSON)
public class ContrasenaService {

    public final IConstrasenaLogic contrasenaLogic;

    public ContrasenaService() {
        this.contrasenaLogic = new ContrasenaLogic();
    }

    @POST
    public OrdenDTO add(OrdenDTO dto) {
        return contrasenaLogic.add(dto);
    }

    @PUT
    public OrdenDTO update(OrdenDTO dto) {
        return contrasenaLogic.update(dto);
    }

    @DELETE
    @Path("/borrar/{idUnidad}/{idInmueble}/{idDispositivo}/{numclave}")
    public Response delete(@PathParam("idUnidad") String idUnidad, @PathParam("idInmueble") String idInmueble,
                           @PathParam("idDispositivo") String idDispositivo, @PathParam("numclave") String numclave
    ) {
        try {
            contrasenaLogic.delete(idUnidad, idInmueble, idDispositivo, numclave);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Password was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(ContrasenaService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }

    @DELETE
    @Path("/borrar/{idUnidad}/{idInmueble}/{idDispositivo}/")
    public Response delete(@PathParam("idUnidad") String idUnidad, @PathParam("idInmueble") String idInmueble,
                           @PathParam("idDispositivo") String idDispositivo) {
        try {
            contrasenaLogic.deleteAll(idUnidad, idInmueble, idDispositivo);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Passwords were deleted").build();
        } catch (Exception e) {
            Logger.getLogger(ContrasenaService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
