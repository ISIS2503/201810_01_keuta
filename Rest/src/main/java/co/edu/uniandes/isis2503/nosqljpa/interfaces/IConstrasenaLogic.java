package co.edu.uniandes.isis2503.nosqljpa.interfaces;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;

public interface IConstrasenaLogic {
    OrdenDTO add(OrdenDTO dto, String usuario) throws Exception;

    OrdenDTO update(OrdenDTO dto, String usuario) throws Exception;

    void delete(OrdenDTO dto, String usuario) throws Exception;

    void deleteAll(OrdenDTO dto, String usuario) throws Exception;
}
