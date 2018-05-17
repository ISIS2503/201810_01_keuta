/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.logic;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IOrdenLogic;
import static co.edu.uniandes.isis2503.nosqljpa.model.dto.converter.OrdenConverter.CONVERTER;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;
import co.edu.uniandes.isis2503.nosqljpa.persistence.OrdenPersistence;
import java.util.List;

/**
 *
 * @author sp.joven
 */
public class OrdenLogic implements IOrdenLogic {
    
     private final OrdenPersistence persistence;

    public OrdenLogic() {
        this.persistence = new OrdenPersistence();
    }
   
      @Override
    public OrdenDTO add(OrdenDTO dto) {
        OrdenDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public OrdenDTO update(OrdenDTO dto) {
        OrdenDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public OrdenDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public List<OrdenDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }
}
