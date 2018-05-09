/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.interfaces;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UsuarioDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.UsuarioEntity;
import java.util.List;

/**
 *
 * @author sp.joven
 */
public interface IUsuarioConverter {
 
    public UsuarioDTO entityToDto(UsuarioEntity entity);

    public UsuarioEntity dtoToEntity(UsuarioDTO dto);

    public List<UsuarioDTO> listEntitiesToListDTOs(List<UsuarioEntity> entities);

    public List<UsuarioEntity> listDTOsToListEntities(List<UsuarioDTO> dtos);
}
