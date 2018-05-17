/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IOrdenLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.OrdenLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;
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

@Path("/horario")
@Produces(MediaType.APPLICATION_JSON)
public class OrdenService {
     private final IOrdenLogic sensorLogic;
     
     
    public OrdenService() {
        this.sensorLogic = new OrdenLogic();
    }

    @POST
    public OrdenDTO add(OrdenDTO dto) {
        return sensorLogic.add(dto);
    }
    
    @PUT
    public OrdenDTO update(OrdenDTO dto) {
        return sensorLogic.update(dto);
    }
    
    @PUT
    @Path("/{id}/{fechaYhoraInicial}/{fechaYhoraFinal}")
    public OrdenDTO updateHorarios( @PathParam("id") String id,@PathParam("fechaYhoraInicial") String fechaYhoraInicial,@PathParam("fechaYhoraFinal") String fechaYhoraFinal ) {
       OrdenDTO dto = sensorLogic.find(id);
       dto.setFechaYhoraInicial(fechaYhoraInicial);
       dto.setFechaYhoraFinal(fechaYhoraFinal);
        return sensorLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    public OrdenDTO find(@PathParam("id") String id) {
        return sensorLogic.find(id);
    }

    @GET
    public List<OrdenDTO> all() {
        return sensorLogic.all();
    }
    
  
    
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
       
            sensorLogic.delete(id);
            
        
    }
}
