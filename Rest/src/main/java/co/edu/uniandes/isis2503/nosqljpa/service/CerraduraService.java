/*
 * The MIT License
 *
 * Copyright 2018 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.auth.AuthorizationFilter.Role;
import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.ICerraduraLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.CerraduraLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.CerraduraDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.InmuebleYUnidadDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UnidadDTO;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
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
 * @author ca.mendoza968
 */
@Path("/cerradura")
//@Secured({Role.administrador, Role.propietario, Role.seguridad, Role.yale})
@Produces(MediaType.APPLICATION_JSON)
public class CerraduraService {
    private final ICerraduraLogic sensorLogic;

    public CerraduraService() {
        this.sensorLogic = new CerraduraLogic();
    }

    @POST
    public CerraduraDTO add(CerraduraDTO dto) {
        return sensorLogic.add(dto);
    }
    
    @PUT
    public CerraduraDTO update(CerraduraDTO dto) {
        return sensorLogic.update(dto);
    }
   
    @PUT
    @Path("/{id}/clave_nueva")
    public CerraduraDTO updateAddClave(@PathParam("id") String id,  String clave) {
        
        CerraduraDTO dto = sensorLogic.find(id);
        String con = (clave.substring(12, clave.length()-5)).replaceAll("\"", "");
        if(dto.getClave1()==null){
        dto.setClave1(con);}
        else if(dto.getClave2()==null){
        dto.setClave2(con);}
        else if(dto.getClave3()==null){
        dto.setClave3(con);}
        else{
        dto.setClave4(con);}
        return sensorLogic.update(dto);
    }
      @PUT
    @Path("/{id}/eliminar_clave/{id_clave}")
    public CerraduraDTO updateDeleteClave(@PathParam("id") String id, @PathParam("id_clave") Integer idClave) {
        
        CerraduraDTO dto = sensorLogic.find(id);
        if(idClave==1){
        dto.setClave1(null);}
        else if(idClave==2){
        dto.setClave2(null);}
        else if(idClave==3){
        dto.setClave3(null);}
        else{
        dto.setClave4(null);}
        return sensorLogic.update(dto);
    }

      @PUT
    @Path("/{id}/cambiar_clave/{id_clave}")
    public CerraduraDTO updateCambiarClave(@PathParam("id") String id, @PathParam("id_clave") Integer idClave, String claveNueva) {
        
        CerraduraDTO dto = sensorLogic.find(id);
        String con = (claveNueva.substring(12, claveNueva.length()-3)).replaceAll("\"", "");
        if(idClave==1){
        dto.setClave1(con);}
        else if(idClave==2){
        dto.setClave2(con);}
        else if(idClave==3){
        dto.setClave3(con);}
        else{
        dto.setClave4(con);}
        return sensorLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    public CerraduraDTO find(@PathParam("id") String id) {
        return sensorLogic.find(id);
    }

    @GET
    public List<CerraduraDTO> all() {
        return sensorLogic.all();
    }
    
    @POST
    @Path("/unidad")
    public List<CerraduraDTO> unidad(UnidadDTO dto) {
        return sensorLogic.findUnidad(dto.getUnidadResidencial());
    }
    
    @POST
    @Path("/inmuebleyunidad")
    public List<CerraduraDTO> unidad(InmuebleYUnidadDTO dto) {
        return sensorLogic.findUnidadYResidencia(dto.getUnidadResidencial(), dto.getNumeroInmueble());
    }
    
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
      
            sensorLogic.delete(id);
    }    
    
}
