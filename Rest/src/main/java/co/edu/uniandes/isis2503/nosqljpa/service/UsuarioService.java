/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;


import co.edu.uniandes.isis2503.nosqljpa.interfaces.IUsuarioLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.UsuarioLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UsuarioDTO;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author sp.joven
 */
@Path("/usuario")

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
    @Path("/{id}")
    public UsuarioDTO update(UsuarioDTO dto,@PathParam("id") String id) {
        return sensorLogic.update(dto);
    }
    
    @PUT
    @Path("/{id}/contrasena")
    public UsuarioDTO updateContrasena(@PathParam("id") String id,  String contrasena) {
        
        UsuarioDTO dto = sensorLogic.find(id);
         String con = (contrasena.substring(17, contrasena.length()-2)).replaceAll("\"", "");
        dto.setContrasena(con);
        return sensorLogic.update(dto);
    }
    

    @GET
    @Path("/{id}")
    public UsuarioDTO find(@PathParam("id") String id) {
        return sensorLogic.find(id);
    }
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
        
            sensorLogic.delete(id);
       
    }    
}
 
