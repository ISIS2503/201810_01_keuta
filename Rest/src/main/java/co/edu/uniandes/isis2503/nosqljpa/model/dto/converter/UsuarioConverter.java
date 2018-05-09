/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.dto.converter;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IUsuarioConverter;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UsuarioDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.UsuarioEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sp.joven
 */
public class UsuarioConverter implements IUsuarioConverter{
        public static IUsuarioConverter CONVERTER = new UsuarioConverter();

    public UsuarioConverter() {
    }

    
    public UsuarioDTO entityToDto(UsuarioEntity entity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setCorreo(entity.getCorreo());
        dto.setTelefono(entity.getTelefono());
        dto.setNombre(entity.getNombre());
        
        return dto;
    }

    
    public UsuarioEntity dtoToEntity(UsuarioDTO dto) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(dto.getId());
       dto.setCorreo(entity.getCorreo());
        dto.setTelefono(entity.getTelefono());
        dto.setNombre(entity.getNombre());
       
        return entity;
    }


    public List<UsuarioDTO> listEntitiesToListDTOs(List<UsuarioEntity> entities) {
        ArrayList<UsuarioDTO> dtos = new ArrayList<>();
        for (UsuarioEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    
    public List<UsuarioEntity> listDTOsToListEntities(List<UsuarioDTO> dtos) {
        ArrayList<UsuarioEntity> entities = new ArrayList<>();
        for (UsuarioDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}

