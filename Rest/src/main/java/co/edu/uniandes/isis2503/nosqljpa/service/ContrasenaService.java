package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.Utils.Utils;
import co.edu.uniandes.isis2503.nosqljpa.auth.AuthorizationFilter.Role;
import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IConstrasenaLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.ContrasenaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("contrasena")
@Produces(MediaType.APPLICATION_JSON)
public class ContrasenaService {

    public final IConstrasenaLogic contrasenaLogic;

    public ContrasenaService() {
        this.contrasenaLogic = new ContrasenaLogic();
    }

    @POST
    @Secured({Role.propietario})
    public OrdenDTO add(OrdenDTO dto, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try {
            String usuario = Utils.getUsernameFromToken(token);
            if (usuario != ""){
                return contrasenaLogic.add(dto, usuario);
            } else {
                throw new WebApplicationException(
                        Response.status(Response.Status.FORBIDDEN).build());
            }
        } catch (Exception e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN).build());
        }
    }

    @PUT
    @Secured({Role.propietario})
    public OrdenDTO update(OrdenDTO dto, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try {
            String usuario = Utils.getUsernameFromToken(token);
            if (usuario != ""){
                return contrasenaLogic.update(dto, usuario);
            } else {
                throw new WebApplicationException(
                        Response.status(Response.Status.FORBIDDEN).build());
            }
        } catch (Exception e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN).build());
        }
    }

    @DELETE
    @Secured({Role.propietario})
    @Path("/borrar/{idUnidad}/{idInmueble}/{idDispositivo}/{numclave}")
    public Response delete(@PathParam("idUnidad") String idUnidad, @PathParam("idInmueble") String idInmueble,
                           @PathParam("idDispositivo") String idDispositivo, @PathParam("numclave") String numclave
            , @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try {
            String usuario = Utils.getUsernameFromToken(token);
            if (usuario != "") {
                contrasenaLogic.delete(new OrdenDTO(idUnidad, idInmueble, idDispositivo, new Integer(numclave), 0), usuario);
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Password was deleted").build();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            Logger.getLogger(ContrasenaService.class.getName()).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }

    @DELETE
    @Secured({Role.propietario})
    @Path("/borrar/{idUnidad}/{idInmueble}/{idDispositivo}/")
    public Response delete(@PathParam("idUnidad") String idUnidad, @PathParam("idInmueble") String idInmueble,
                           @PathParam("idDispositivo") String idDispositivo, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try {
            String usuario = Utils.getUsernameFromToken(token);
            if (usuario != "") {
                contrasenaLogic.deleteAll(new OrdenDTO(idUnidad, idInmueble, idDispositivo, 0, 0), usuario);
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Passwords were deleted").build();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            Logger.getLogger(ContrasenaService.class.getName()).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
