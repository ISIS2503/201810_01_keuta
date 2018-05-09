/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.interfaces;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UsuarioDTO;

/**
 *
 * @author sp.joven
 */
public interface IUsuarioLogic {
    public UsuarioDTO add(UsuarioDTO dto);
    public UsuarioDTO update(UsuarioDTO dto);
    public UsuarioDTO find(String id);
    public Boolean delete(String id);
}
