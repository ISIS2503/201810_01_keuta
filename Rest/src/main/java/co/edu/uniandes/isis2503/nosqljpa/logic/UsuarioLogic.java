/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.logic;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UsuarioDTO;
import co.edu.uniandes.isis2503.nosqljpa.persistence.UsuarioPersistence;
import static co.edu.uniandes.isis2503.nosqljpa.model.dto.converter.UsuarioConverter.CONVERTER;
import java.util.List;
import java.util.UUID;



/**
 *
 * @author sp.joven
 */
public class UsuarioLogic {
     private final UsuarioPersistence persistence;

    public UsuarioLogic() {
        this.persistence = new UsuarioPersistence();
    }

   
    
    public UsuarioDTO add(UsuarioDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        UsuarioDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    
    public UsuarioDTO update(UsuarioDTO dto) {
       UsuarioDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    
    public UsuarioDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    
    public List<UsuarioDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    
    public Boolean delete(String id) {
        return persistence.delete(id);
    }

    
    public List<UsuarioDTO> findUnidad(String idUnidad) {
        return CONVERTER.listEntitiesToListDTOs(persistence.darUnidad(idUnidad));
    }
}

