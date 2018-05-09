/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.auth.AuthorizationFilter;
import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IInmuebleLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IUsuarioLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.InmuebleLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.UsuarioLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.InmuebleDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UnidadDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UsuarioDTO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author sp.joven
 */
@Path("/usuario")
@Secured({AuthorizationFilter.Role.propietario})
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioService {

    private final IUsuarioLogic sensorLogic;

    public UsuarioService() {
        this.sensorLogic = (IUsuarioLogic) new UsuarioLogic();
    }

    @POST
    public UsuarioDTO add(UsuarioDTO dto) {
        return sensorLogic.add(dto);
    }
    
    @PUT
    public UsuarioDTO update(UsuarioDTO dto) {
        return sensorLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    public UsuarioDTO find(@PathParam("id") String id) {
        return sensorLogic.find(id);
    }

 
    

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            sensorLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Sensor was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(InmuebleService.class.getName()).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }    
}
 
